package nc.ird.malariaplantdb.domain.util.comparator;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Supplier;

/**
 * Comparator which order an collection by a lexicographical way
 *
 * @param <T> The type object used in the collection which is compared
 */
class CollectionComparator<T> implements Comparator<Collection<T>> {

    private final Supplier<? extends Comparator<T>> comparatorSupplier;

    /**
     * Constructor
     *
     * @param comparatorSupplier A comparator supplier for the T parametrized type
     */
    CollectionComparator(Supplier<? extends Comparator<T>> comparatorSupplier){
        this.comparatorSupplier = comparatorSupplier;
    }

    @Override
    public int compare(Collection<T> o1, Collection<T> o2) {
        Iterator<T> i1 = o1.iterator();
        Iterator<T> i2 = o2.iterator();
        int result;

        do {
            if (!i1.hasNext()) {
                if (!i2.hasNext()) return 0; else return -1;
            }
            if (!i2.hasNext()) {
                return 1;
            }

            result = comparatorSupplier.get().compare(i1.next(), i2.next());
        } while (result == 0);

        return result;
    }

}
