public class Main{
	private static class Edge {
		public int x;

		public Edge(int x) {
			this.x = x;
		}

		public String toString() {
			return String.valueOf(x);
		}
	}

	public static void main(String[] args) {
		Edge a = new Edge(233);
		Edge b = new Edge(233);
		System.err.println(a == b);
		System.err.println(a.equals(b));
	}

}