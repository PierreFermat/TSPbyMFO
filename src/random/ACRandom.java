package random;

class ACRandom extends random.Random
{
    public final static long k_iLinear = 31;
    public final static int k_iSizeOfCache = 55;
    public final static int k_iMaxOfCache = k_iSizeOfCache - 1;
    public final static int k_iMidOfCache = (k_iSizeOfCache>>1) + 1;

    long m_iVal[] = new long [k_iSizeOfCache];
    int m_iGenCounter = 0;

    public ACRandom(long seed)
    {
        super();
        m_iVal[0] = seed;
        for (int j = 1; j < k_iSizeOfCache; j++)
        {
            m_iVal[j] = (Mult(k_iLinear, m_iVal[j-1]) + 1) & m_iMod_Shift_Mask;
        }
        m_iGenCounter = k_iSizeOfCache - 1;
    }

    public int NextInt(int max)
    {
        m_iGenCounter = (m_iGenCounter + 1) % k_iSizeOfCache;

        m_iVal[m_iGenCounter] = (m_iVal[(m_iGenCounter + k_iMidOfCache) % k_iSizeOfCache] + m_iVal[(m_iGenCounter + k_iMaxOfCache) % k_iSizeOfCache]) & m_iMod_Shift_Mask;

        long re = (m_iVal[m_iGenCounter]* max) >> m_iMod_Shift;
        return (int)re;
    }
}
