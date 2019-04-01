package administrare;

import java.util.Comparator;

public class SortareProduse implements Comparator<Produs> {

    @Override
    public int compare(Produs p1, Produs p2) {
        if(p1.getPretProdus()>p2.getPretProdus())
            return 1;
        else if(p1.getPretProdus()==p2.getPretProdus())
            return 0;
        else
            return -1;
    }
}
