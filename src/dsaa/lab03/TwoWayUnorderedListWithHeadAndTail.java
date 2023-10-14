package dsaa.lab03;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class TwoWayUnorderedListWithHeadAndTail<E> implements IList<E>{

    private class Element{
        public Element(E e) {
            this.object = e;
        }
        public Element(E e, Element next, Element prev) {
            this.object = e;
            this.prev = prev;
            this.next = next;
        }
        E object;
        Element next=null;
        Element prev=null;
    }

    Element head;
    Element tail;
    // can be realization with the field size or without
    int size;

    private class InnerIterator implements Iterator<E>{
        Element pos, last;
        int index;

        public InnerIterator() {
            pos = head;
            index = 0;
        }
        @Override
        public boolean hasNext() {
            return (index < size);
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();

            last = pos;
            pos = pos.next;
            index++;
            return last.object;
        }
    }

    private class InnerListIterator implements ListIterator<E>{
        Element pos, last;
        int index;

        public InnerListIterator() {
            pos = head;
            index = 0;
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();

        }

        @Override
        public boolean hasNext() {
            return (index < size);
        }

        @Override
        public boolean hasPrevious() {
            return (index > 0);
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();

            last = pos;
            pos = pos.next;
            index++;
            return last.object;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            if(pos == null)
            {
                pos = tail;
            }
            else
            {
                pos = pos.prev;
            }
            last = pos;
            index--;
            return last.object;
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();

        }

        @Override
        public void set(E e) {
            if(last == null) throw new NoSuchElementException();
            last.object = e;

        }
    }

    public TwoWayUnorderedListWithHeadAndTail() {
        // make a head and a tail
        head=null;
        tail=null;
    }

    @Override
    public boolean add(E e) {
        Element element = new Element(e, null, tail);
        if(size == 0)
        {
            head = element;
            tail = element;
            size++;
            return true;
        }
        tail.next = element;
        tail = element;
        size++;
        return true;
    }

    private Element find(int index)
    {
        if(index < 0 || index >= size) throw new NoSuchElementException();
        Element res = head;
        for(int i = 0; i < index; i++)
        {
            res = res.next;
        }
        return res;
    }

    @Override
    public void add(int index, E e) {
        if(index > size || index < 0) throw new NoSuchElementException();
        if(index == size)
        {
            add(e);
            return;
        }
        Element element = new Element(e);
        if(index == 0)
        {
            head.prev = element;
            element.next = head;
            head = element;
            size++;
            return;
        }
        Element prev = find(index);
        prev.next.prev = element;
        element.next = prev.next;
        element.prev = prev;
        prev.next = element;
        size++;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean contains(E e) {
        return indexOf(e) != -1;
    }

    @Override
    public E get(int index) {
        return find(index).object;
    }

    @Override
    public E set(int index, E e) {
        Element element = find(index);
        E res = element.object;
        element.object = e;
        return res;
    }

    @Override
    public int indexOf(E e) {
        int index = 0;
        for (Element x = head; x != null; x = x.next) {
            if (e.equals(x.object))
                return index;
            index++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        if(index < 0 || index >= size) throw new NoSuchElementException();
        Element element = find(index);
        E res = element.object;
        size--;
        if(element == head)
        {
            head = head.next;
            head.prev = null;
        }
        else if(element == tail)
        {
            tail = tail.prev;
            tail.next = null;
        }
        else
        {
            element.prev.next = element.next;
            element.next.prev = element.prev;
        }
        return res;
    }

    @Override
    public boolean remove(E e) {
        int a = indexOf(e);
        if(a == -1) return false;
        remove(a);
        return true;
    }

    public void removeEven()
    {
        if(head == null) return;
        Element current = head.next, previous = null;
        head = head.next;
        if(head != null) head.prev = null;
        size--;
        while(current != null && current.next != null)
        {
            size--;
            current.next = current.next.next;
            if(current.next != null) current.next.prev = current;
            previous = current;
            current = current.next;
        }
        if(current != null) tail = current;
        else tail = previous;
    }

    @Override
    public int size() {
        return size;
    }
    public String toStringReverse() {
        ListIterator<E> iter=new InnerListIterator();
        while(iter.hasNext())
            iter.next();
        StringBuilder retStr = new StringBuilder("");
        while(iter.hasPrevious())
        {
            retStr.append("\n").append(iter.previous());
        }
        return retStr.toString();
    }

    public void add(TwoWayUnorderedListWithHeadAndTail<E> other) {
        if(other == this || other.size == 0) return;
        if(size == 0)
        {
            head = other.head;
        }
        else
        {
            other.head.prev = tail;
            tail.next = other.head;
        }
        tail = other.tail;
        size += other.size;
        other.clear();
    }
}
