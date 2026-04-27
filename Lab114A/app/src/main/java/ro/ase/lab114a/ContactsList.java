package ro.ase.lab114a;

import java.util.ArrayList;

public class ContactsList {

    static ArrayList<Contact> lista = new ArrayList<>();
    public ContactsList(){}

    public void adaugaContact(Contact c)
    {
        lista.add(c);
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(Contact c: lista)
        {
            sb.append(c.getIdc());
            sb.append(",");
            sb.append(c.getNumec());
            sb.append(",");
            sb.append(c.getTelefonc());
            sb.append("\n");
        }
        return sb.toString();
    }


}
