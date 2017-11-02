package nc.ird.malariaplantdb.service.xls.structures;

import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Give an access to the list of the class specified
 *
 * @author acheype
 */
public class ClassMap {

    private Map<Class<?>, List<Object>> objLists = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> void putList(@NonNull Class<T> clazz, @NonNull List<T> instance) {
        objLists.put(clazz, (List<Object>) instance);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getList(@NonNull Class<T> clazz) {
        return (List<T>) objLists.get(clazz);
    }

    public int size() {
        return objLists.size();
    }

}
