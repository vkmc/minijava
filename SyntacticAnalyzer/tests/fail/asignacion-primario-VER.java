class foo {
	ctor(int n1)
	var int n2, n3, n4, n5, n6, n7;
	{
		n2 = this;
		n3 = 'a';
		n4 = "hola";
		n5 = n1 + 1 - 2 / 8 * 9 % 2;
		n6 = true && false >= i < 3; // aca deberia fallar
		n7 = (new objeto(true).test(n1,n2).objeto());
		n1 = objeto(n2).objeto().set("foo1");
	}
}
