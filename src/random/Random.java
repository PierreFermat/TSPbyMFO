package random;

public class Random
{
    public final static int m_iMod_Shift = 30;
    public final static long m_iMod_Shift_Mask = 0x3FFFFFFFL;
    public final static int m_iHaftMod_Shift = 15;
    public final static long m_iHaftMod_Shift_Mask = 0x7FFFL;

    public Random()
    {}

    public long Mult(long a, long b)
    {
        long a1 = (long)a >> m_iHaftMod_Shift;
        long a0 = a & m_iHaftMod_Shift_Mask;

        long b1 = (long) b >> m_iHaftMod_Shift;
        long b0 = (long) b & m_iHaftMod_Shift_Mask;

        long re = ((((a1*b0 + a0*b1) & m_iHaftMod_Shift_Mask ) << m_iHaftMod_Shift) + a0*b0) & m_iMod_Shift_Mask;
        return re;
    }

    public int NextInt(int max)
    {return 0;}
}





