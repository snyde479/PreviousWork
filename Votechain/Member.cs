using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;

namespace Votechain
{
    class Member 
    {

        public const int T_SIZE=20;
        List<String> inputs;
        List<String> outputs;
        int number;
        StreamReader file;
        const int MAX_TIME = 1000;
        bool requested = false;
        bool done = false;
        List<Block> blocks = new List<Block>();
        List<String> transactions = new List<string>();
        public Member(ref List<String> ins, ref List<String> outs, int number)
        {
            this.inputs = ins;
            this.outputs = outs;
            this.number = number;
            file = new StreamReader(@"C:\votes\" + number + ".txt");
        }

        public List<Block> getChain()
        {
            return blocks;
        }
        public Block getBlock()
        {
            return blocks.Last();
        }

        public void run() {
            blocks.Add(Block.makeGenesis());
            //TODO CONNECT
            int index = 0;
            Random r = new Random();
            DateTime nextTime = DateTime.Now.AddMilliseconds(r.Next()%MAX_TIME);
            bool keepGoing = true;
            while (keepGoing)//Change to connection has next?
            {
                if (!done)
                {
                    done = readTransaction(ref blocks, ref r, ref nextTime);
                }
                if (index != inputs.Count())
                {
                    String input = inputs.ElementAt(index);//Read from scanner
                    if (input == null)
                    {
                        continue;
                    }
                    if (input.StartsWith("Transaction"))
                    {
                        verifyTransaction(ref blocks, input);
                    }
                    else if (input.StartsWith("Block"))
                    {
                        addBlock(ref blocks,Int32.Parse(input.Split(' ').Last()));
                    }
                    else if (input.StartsWith("Request Chain"))
                    {
                        PseudoNetwork.setChain(ref blocks);
                        outputs.Add("Response Chain "+number);
                    }
                    else if (requested&&input.StartsWith("Response Chain"))
                    {
                        int num = Int32.Parse(input.Split(' ').Last());
                        blocks = confirmChain(blocks, ChainStarter.members[num].getChain(), ref transactions, num);
                        requested = false;
                    }
                    else if (input.StartsWith("End"))
                    {
                        keepGoing = false;
                    }
                    index++;
                }
            }
        }

        private List<Block> confirmChain(List<Block> currentChain, List<Block> newChain, ref List<String> transactions, int num)
        {
            List<String> transactionList = new List<string>();
            for(int i = 1; i < newChain.Count(); i++)  //Invalid voter
            {
                String[] tempList = newChain.ElementAt(i).getData().Split('\n');
                for(int j = 0; j < transactionList.Count() - 1; j++)
                {
                    if(!Validator.isVoter(Int32.Parse(tempList[j].Split(' ')[0])))
                    {
                        Console.WriteLine("Invalid block in chain");
                        return currentChain;
                    }
                    else
                    {
                        transactionList.Add(tempList[j]);
                    }
                }
            }

            for(int i = 1; i < currentChain.Count(); i++) //Trying to change a vote
            {
                String[] tempList = currentChain.ElementAt(i).getData().Split('\n');
                for (int j = 0; j < tempList.Count() - 1; j++)
                {
                    for(int m = 0; m < transactionList.Count(); m++)
                    {
                        if(transactionList[m].Split(' ')[0].Equals(tempList[j].Split(' ')[0]) && !transactionList[m].Equals(tempList[j]))
                        {
                            return currentChain;
                        }
                    }

                    for(int m = 0; m < transactions.Count(); m++)
                    {
                        if (transactions[m].Split(' ')[0].Equals(tempList[j].Split(' ')[0]) && !transactions[m].Equals(tempList[j]))
                        {
                            return currentChain;
                        }
                    }
                }
            }

            for(int i = 0; i < transactionList.Count(); i++) //Double voting
            {
                for(int j = i + 1; j < transactionList.Count(); j++)
                {
                    if(transactionList[i].Split(' ')[0].Equals(transactionList[j].Split(' ')[0]))
                    {
                        return currentChain;
                    }
                }
            }

            for (int i = 1; i < currentChain.Count(); i++) //Making sure to not lose transactions
            {
                String[] tempList = currentChain.ElementAt(i).getData().Split('\n');
                for (int j = 0; j < tempList.Count() - 1; j++)
                {
                    transactions.Add(tempList[j]);
                }
            }

            for(int i = 0; i < transactionList.Count(); i++) //Remove transactions to prevent duplicates
            {
                for(int j = transactions.Count()-1; j >=0; j--)
                {
                    if (transactionList.ElementAt(i).Equals(transactions.ElementAt(j)))
                    {
                        transactions.RemoveAt(j);
                    }
                }
            }
            return newChain;
        }

        private void addBlock(ref List<Block> blocks, int from)
        {
            Block b = ChainStarter.members[from].getBlock();
            for(int i = 0; i < b.getData().Split('\n').Count()-1; i++)
            {
                if(!isValidForBlock(blocks, b.getData().Split('\n')[i]))
                {
                    return;
                }
            }
            if (blocks.ElementAt(blocks.Count - 1).isValidNewBlock(b))
            {
                blocks.Add(b);
                for (int i = 0; i < T_SIZE; i++)
                {
                    String d = b.getData().Split('\n')[i];
                    for (int j = transactions.Count()-1; j>=0; j--)
                    {
                        if (transactions.ElementAt(j).Equals(d))
                        {
                            transactions.RemoveAt(j);
                        }
                    }
                }
            }else
            {
                outputs.Add("Request Chain");
                requested = true;
            }
        }

        private void verifyTransaction(ref List<Block> blocks, string input)
        {
            String transaction = input.Substring("Transaction".Length + 1);
            bool valid = isValid(blocks, transaction);
            if (valid)
            {
                transactions.Add(transaction);
                if (transactions.Count()>=20)
                {
                    makeBlock(ref blocks);
                }
            }
        }

        private void makeBlock(ref List<Block> blocks)
        {
            String data = "";
            for (int i = transactions.Count()-1; i >=0; i--)
            {
                data += transactions.ElementAt(i) + "\n";
                transactions.RemoveAt(i);
            }
            Block b = blocks.ElementAt(blocks.Count - 1);
            Block block = b.generateNextBlock(data);
            blocks.Add(block);
            PseudoNetwork.setBlock(b);//Send block
            outputs.Add("Block " + number);
        }

        private bool isValid(List<Block> blocks, string transaction)
        {
            bool valid = Validator.isVoter(Int32.Parse(transaction.Split(' ')[0]));
            for (int i = 1; valid && i < blocks.Count(); i++)
            {
                String[] data = blocks.ElementAt(i).getData().Split('\n');
                for (int j = 0; valid && j < data.Count() - 1; j++)
                {
                    if (data[j].StartsWith(transaction.Split(' ')[0] + " "))
                    {
                        valid = false; //Already voted
                    }
                }
            }
            for (int i = 0; valid && i < transactions.Count(); i++)
            {
                if (transactions.ElementAt(i).StartsWith(transaction.Split(' ')[0] + " "))
                {
                    valid = false;//Vote is pending
                }
            }

            return valid;
        }

        private bool isValidForBlock(List<Block> blocks, string transaction)
        {
            bool valid = Validator.isVoter(Int32.Parse(transaction.Split(' ')[0]));
            for (int i = 1; valid && i < blocks.Count(); i++)
            {
                String[] data = blocks.ElementAt(i).getData().Split('\n');
                for(int j = 0; valid&&j < data.Count() - 1; j++)
                {
                    if (data[j].StartsWith(transaction.Split(' ')[0] + " "))
                    {
                        valid = false; //Already voted
                    }
                }
            }
            return valid;
        }

        private bool readTransaction(ref List<Block> blocks, ref Random r, ref DateTime nextTime)
        {
            if (DateTime.Now > nextTime)
            {
                String line = file.ReadLine();
                if (line != null&&!line.Equals(""))
                {
                    outputs.Add("Transaction " + line);
                    transactions.Add(line);
                    if (transactions.Count()>=20)
                    {
                        makeBlock(ref blocks); 
                    }
                }
                else
                {
                    outputs.Add("Done");
                    Validator.finished();
                    Console.WriteLine("Done with votes from " + number);
                    return true;
                }
                nextTime = DateTime.Now.AddMilliseconds(r.Next()%MAX_TIME);
            }
            return false;
        }
    }
}
