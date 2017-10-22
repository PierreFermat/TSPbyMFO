package random;

class LCRandom extends random.Random
{
    public final static long k_iLinear = ((long)1<<28) + 21;


    long m_iVal;

    public LCRandom(long seed)
    {
        super();
        m_iVal = seed;
    }

    public int NextInt(int max)
    {
        m_iVal = (Mult(m_iVal , k_iLinear) + 1)  & m_iMod_Shift_Mask;
        long re = (m_iVal* max) >> m_iMod_Shift;
        return (int)re;
    }
}
