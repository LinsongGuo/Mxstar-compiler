public class Main {
	public static void main(String[] args)  {
		int[] a;
		update(a);
		for (int i = 0; i < 10; ++i) {
			System.out.println(a[i] + " ");			
		}
	}

	public void update(int[] a) {
		a = new int[10];
	}
}
