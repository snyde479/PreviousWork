using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Votechain
{
    class Block
    {
        protected int index;
        protected int hash;
        protected DateTime timestamp;
        protected String data;
        protected int previousHash;

        protected Block() { }

        public Block(int index, int previousHash, DateTime timestamp, String data)
        {
            this.index = index;
            this.previousHash = previousHash;
            this.timestamp = timestamp;
            this.data = data;
            this.hash = generateHash(index, previousHash, timestamp, data);
        }

        public Block generateNextBlock(String newData)
        {
            return (new Block(index+1, hash, DateTime.Now, newData));
        }

        protected static int generateHash(int index, int previousHash, DateTime timestamp, String data)
        {
            return (index + "" + previousHash + "" + timestamp + "" + data).GetHashCode();
        }

        int calculateHash(String data)
        {
            return (index+""+hash+""+timestamp+""+data).GetHashCode();
        }

        public static Block makeGenesis()
        {
            return new Block(0, 0, DateTime.Now, "Initial block");
        }

        public String getData()
        {
            return data;
        }

        public override int GetHashCode()
        {
            return hash;
        }

        public bool isValidNewBlock(Block newBlock)
        {
            if (newBlock.index != this.index + 1)
            { //It's not the next block
                return false;
            }
            if (newBlock.previousHash != this.hash)
            { //This wasn't the previous block
                return false;
            }
            if (calculateHash(newBlock.data) != newBlock.hash)
            { //The hash is wrong (fake block possibly)
                return false;
            }
            return true;
        }
    }
}
