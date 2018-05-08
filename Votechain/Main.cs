using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;

namespace Votechain
{
    class ChainStarter
    {
        const int NUM_THREADS = 10;
        public static Member[] members = new Member[NUM_THREADS];
        static void Main(String[] args)
        {
            Validator.initialize();
            List<String>[] inputs = new List<String>[NUM_THREADS];
            List<String>[] outputs = new List<String>[NUM_THREADS];
            int[] indices = new int[NUM_THREADS];
            Thread[] threads = new Thread[NUM_THREADS];
            Member first = null;
            bool allTransfered = true;
            for (int i = 0; i < NUM_THREADS; i++)
            {
                indices[i] = 0;
                inputs[i] = new List<String>();
                outputs[i] = new List<String>();
                Member m = new Member(ref inputs[i], ref outputs[i], i);
                members[i] = m;
                if (i == 0) { first = m; }
                threads[i] = new Thread(m.run);
                threads[i].Start();
                Validator.connect();
                Console.WriteLine("Started thread " + i);
            }

            while (!Validator.allDone()||!allTransfered)
            {
                allTransfered = true;
                for(int i = 0; i < NUM_THREADS; i++)
                {
                    if (outputs[i].Count() != indices[i])
                    {
                        outputs[i].ElementAt(indices[i]);
                        for (int j = 0; j < NUM_THREADS; j++)
                        {
                            if (i != j)
                            {
                                allTransfered = false;
                                inputs[j].Add(outputs[i].ElementAt(indices[i]));
                            }
                        }
                        indices[i]++;
                    }
                }
            }
            for (int j = 0; j < NUM_THREADS; j++)
            {
                inputs[j].Add("Request Chain");
            }
            for (int j = 0; j < NUM_THREADS; j++)
            {
                inputs[j].Add("End");
            }
            for (int j = 0; j < NUM_THREADS; j++)
            {
                while (threads[j].ThreadState == ThreadState.Running) ; //Wait for all threads to finish
            }
            List<Block> blocks = first.getChain();
            List<String> votes = new List<string>();
            Console.WriteLine(blocks.Count());//Getting only 2 of the 4 blocks
            for(int i = 0; i < blocks.Count; i++)
            {
                String[] vs = blocks.ElementAt(i).getData().Split('\n');
                for(int j = 0; j < vs.Count(); j++)
                {
                    votes.Add(vs[j]);
                }
            }
            votes.Sort();
            int index = 0;
            while (index < votes.Count()-1)
            {
                if (votes.ElementAt(index).Equals(votes.ElementAt(index + 1)))
                {
                    votes.RemoveAt(index);
                }
                else
                {
                    index++;
                }
            }
            Console.WriteLine(votes.Count());//getting only 2 votes
            for(int i = 0; i < votes.Count(); i++)
            {
                Console.WriteLine(votes.ElementAt(i));
            }
        }
    }
}
