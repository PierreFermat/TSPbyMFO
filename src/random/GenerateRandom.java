package random;

public class GenerateRandom {
    private static LCRandom random = new LCRandom(100);

    public static int getRandomNumber(final int low, final int high)
    {
        return random.NextInt(high-low)+low;
    }


    public static int getExclusiveRandomNumber(final int high, final int except)
    {
        boolean done = false;
        int getRand = 0;

        while(!done)
        {
            getRand = random.NextInt(high);
            if(getRand != except){
                done = true;
            }
        }

        return getRand;
    }
}
