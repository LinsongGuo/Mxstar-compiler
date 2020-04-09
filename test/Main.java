 class A {
	public int val;
}

public class Main {
	/*public static void update(A[] a) {
		a = new A[10];
		for (int i = 0; i < 10; ++i) {
			a[i].val = i;
		}
	}*/

	public static void update(int[] a) {
		a = new A[10];
		for (int i = 0; i < 10; ++i) {
			a[i].val = i;
		}
	}

	public static void main(String[] args)  {
		A[] a = new A[10];
		for (int i = 0; i < 10; ++i) {
			a[i] = new A();
			a[i].val = 0;
		}
		
		for (int i = 0; i < 10; ++i) {
			System.out.println(a[i].val + " ");			
		}
	}
	
}
