class Test {
	test() {

		int i = 2;
		int j = 4;
		
		int k=2;
		int l=4;

		int x;
		boolean cond1, cond2;
		
		x = i * j / i;
		j = x + i - j;
		i = i % x;
		
		k=i*j/i;
		l=x+i-j;
		x=i%x;

		cond1 = i < x;
		cond2 = i > x;
		cond1 = i <= x;
		cond2 = i >= x;

		
		cond1=i<x;
		cond2=i>x;
		cond1=i<=x;
		cond2=i>=x;
		
		cond1 = cond1 && cond2;
		cond2 = !(cond2 || cond1);
		cond1 = i != j;
		cond2 = i == j;
		
		cond1=cond1&&cond2;
		cond2=!(cond2||cond1);
		cond1=i!=j;
		cond2=i==j;	
		
	}
}