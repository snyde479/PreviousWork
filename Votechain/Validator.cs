using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Votechain
{
    class Validator
    {
        private const String fileLocation = @"C:\votes\IDs.txt";
        private static List<int> ids = new List<int>();
        private static int connected = 0;
        private static int done = 0;

        public static void connect()
        {
            connected++;
        }

        public static void finished()
        {
            done++;
        }

        public static bool allDone()
        {
            return connected == done&&connected!=0;
        }

        public static void initialize()
        {
            String[] idList = System.IO.File.ReadAllLines(fileLocation);
            for(int i = 0; i < idList.Count(); i++)
            {
                ids.Add(Int32.Parse(idList[i]));
            }
        }

        public static bool isVoter(int id)
        {
            return ids.Contains(id);
        }
    }
}
