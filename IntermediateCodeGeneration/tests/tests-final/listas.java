// Autores: Mariano Rodecker, Matias Varela

class IntLinkedList{
	var int size;
	var IntElement first, last;
	IntLinkedList(){
		size = 0;
		first = new IntElement(0); //centinela first
		
		(first.setNext(null));
		
		last = null;
	}
	
	dynamic int getSize(){
		return size;
	}
	dynamic boolean addFirst(int e)
	var IntElement element;
	{
		element = new IntElement(e);
		(element.setNext(first.getNext()));
		(first.setNext(element));
		size = size +1;
		if (size == 1)
			last = first.getNext();
		return true;
	}
	
	dynamic boolean addLast(int e)
	var IntElement element;
	{
		element = new IntElement(e);
		(last.setNext(element));
		size = size +1;
		last = element;
		return true;
	}
	
	dynamic boolean add(int index, int e)
	var int i;
	var IntElement current, element;
	{
		if (index >= size || index < 0)
			return false;
		
		if (index == size -1)
			return addLast(e);
		
		element = new IntElement(e);
		current = first.getNext();
		i=0;
		while (i<index){
			current = current.getNext();
			i = i+1;
		}
		
		(element.setNext(current.getNext()));
		(current.setNext(element));
		
		size = size+1;
		
		
		return true;
	}
	
	dynamic int getFirst(){
		return first.getElement();
	}
	dynamic int getLast(){
		return last.getElement();
	}
	dynamic int get(int index)
	var int i;
	var IntElement current;
	{
		current = first.getNext();
		i=0;
		while (i<index){
			current = current.getNext();
			i = i+1;
		}
		return current.getElement();
	}
}
class IntElement{
	var int element;
	var IntElement next;
	
	IntElement(int e){
		element = e;
	}
	
	dynamic void setNext(IntElement n){
		next = n;
	}
	dynamic int getElement(){
		return element;
	}
	dynamic IntElement getNext(){
		return next;
	}
	
}


class MainClass{
	static void main()
	var int i;
	var IntLinkedList list;
	{
		list = new IntLinkedList();
		(list.addFirst(0));
		(list.addLast(1));
		(list.addLast(2));
		(list.addLast(3));
		(list.addLast(4));
		(list.addLast(5));
		
		for(i=0;i<list.getSize();i+1){
			(System.printIln(list.get(i)));
		}
	}
}
