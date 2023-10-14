package dsaa.lab03;

import java.util.Scanner;

public class Document{
    public String name;
    public TwoWayUnorderedListWithHeadAndTail<Link> link;
    public Document(String name, Scanner scan) {
        this.name=name;
        link= new TwoWayUnorderedListWithHeadAndTail<>();
        load(scan);
    }
    public void load(Scanner scan) {
        String line;
        String[] words;
        do
        {
            line = scan.nextLine();
            words = line.split(" ");
            for(String word : words)
            {
                if(word.length() >= 6 && word.substring(0, 5).equalsIgnoreCase("link="))
                {
                    String link1 = word.substring(5);
                    if (correctLink(link1))
                    {
                        link.add(new Link (link1.toLowerCase()));
                    }
                }
            }
        }
        while(!line.equals("eod"));
    }
    public static boolean corretChar(char a)
    {
        return (a >= 'a' && a <= 'z') || (a >= 'A' && a <= 'Z') || (a >= '0' && a <= '9') || a == '_';
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    public static boolean correctLink(String link) {
        char[] letters = link.toCharArray();
        if(!((letters[0] >= 'a' && letters[0] <= 'z') || (letters[0] >= 'A' && letters[0] <= 'Z'))) return false;
        for(char letter : letters)
        {
            if(!corretChar(letter)) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Document: " + name);
        for(Link link1 : link)
        {
            res.append('\n').append(link1.ref);
        }
        return res.toString();
    }

    public String toStringReverse() {
        String retStr="Document: "+name;
        return retStr+link.toStringReverse();
    }

}