package dsaa.lab03;

public class Link{
    public String ref;
    // in the future there will be more fields
    public Link(String ref) {
        this.ref=ref;
    }
    @Override
    public boolean equals(Object link1) {
        if(!(link1 instanceof Link)) return false;
        return (((Link)link1).ref.equals(ref));
    }

    public String toString()
    {
        return ref;
    }

}