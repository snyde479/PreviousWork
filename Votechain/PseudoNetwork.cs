using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Votechain
{
    class PseudoNetwork
    {
        private static Block block;
        public static Block getBlock()
        {
            return block;
        }
        public static void setBlock(Block b)
        {
            block = b;
        }

        private static List<Block> blocks;
        private static bool wait = false;
        public static List<Block> getChain()
        {
            while (wait) { }
            wait = true;
            if (blocks == null)
            {
                wait = false;
                return new List<Block>();
            }
            List<Block> copy = new List<Block>();
            for (int i = 0; i < blocks.Count(); i++)
            {
                copy.Add(blocks.ElementAt(i));
            }
            blocks = null;
            wait = false;
            return copy;
        }
        public static void setChain(ref List<Block> toCopy)
        {
            while (wait) { }
            wait = true;
            blocks = new List<Block>();
            for (int i = 0; i < blocks.Count; i++)
            {
                blocks.Add(toCopy.ElementAt(i));
            }
            wait = false;
        }
    }
}
