//testandtest
import java.util.ArrayList;

class Value1
{
    private int i;
    public void set(int i){this.i = i;}
    public int get() {return i;}
    public String toString() {return ""+i;}
}

public class note
{
    // ������
    private ArrayList<String> notes = new ArrayList<String>();

    public void add(String s)
    {
        notes.add(s);
        // notes.add(10);//error,ֻ����String
    }

    public void add(String s, int location)
    {
        notes.add(location, s);
    }

    public int getSize()
    {
        return notes.size();
    }

    public String getnote(int index)
    {
        return notes.get(index);
    }

    public void removenote(int index)
    {
        notes.remove(index);//���õ�remove��������Ҫɾ���Ķ��󲻴���ʱ�����׳��쳣
    }

    public String[] list()
    {
        int[] ia = new int[10];
        String[] a = new String[notes.size()];
        notes.toArray(a);
        return a;
    }

    public static void main(String[] args)
    {
        note nb = new note();
        nb.add("first");
        nb.add("second");
        nb.add("third", 1);
        System.out.println(nb.getSize());//2
        System.out.println(nb.getnote(1));//third
        System.out.println(nb.getnote(2));//second
        // System.out.println(nb.getnote(10));//java.lang.IndexOutOfBoundsException: Index 10 out of bounds for length 2
        nb.removenote(1);
        System.out.println("note�д洢��������Ϣ:");
        String[] a = nb.list();
        for(String s : a)
        {
            System.out.println(s);
        }
        ArrayList<String> t = new ArrayList<String>();
        t.add("first");
        t.add("second");
        System.out.println("ArrayList��for-eachѭ����");
        for(String rt : t)
        {
            System.out.println(rt);
        }


        int[] ia = new int[5];
        // ���������е�ÿ��Ԫ�ض��Ƕ���Ĺ����ߣ����Ƕ�����
        String[] b = new String[5];//10����������ߣ������������null
        System.out.println(ia[0]);//0
        System.out.println(b[0]);//null
        System.out.println(ia[0]+2);//2
        System.out.println(b[0]+'a');//nulla
        // System.out.println(b[0].length());//java.lang.NullPointerException: Cannot invoke "String.length()" because "b[0]" is null
        for ( int i=0; i<b.length; i++)
        {
            b[i] = ""+i;
        }
        System.out.println(b[0]);//0

        for ( int i=0; i<ia.length; i++)
        {
            ia[i] = 1;
        }

        for ( int i : ia)
        {
            i++;
        }
        System.out.println("����ia�е�Ԫ��Ϊ��");
        for ( int i : ia)
        {
            System.out.println(i);//int�����е�Ԫ��û�б䣬��Ϊ1
        }

        Value1[] c = new Value1[5];
        for ( int i=0; i<c.length; i++)
        {
            c[i] = new Value1();//
            c[i].set(0);
        }
        System.out.println("����c�е�Ԫ��Ϊ��");
        for (Value1 k : c)
        {
            System.out.println(k.get());//0
            k.set(1);
        }
        System.out.println("����c�е�Ԫ��Ϊ��");
        for (Value1 k : c)
        {
            System.out.println(k.get());//1
        }
    }
}