package kevytlaskutus.dao;

import java.sql.*;
import java.util.List;

public interface ProductDao<T, K, S> {
    public List<T> getProductsByInvoiceId(K id) throws SQLException;
    public void saveProductsInBatches(K id, List<T> object) throws SQLException;
    public void updateProductsInBatches(K id, List<T> object) throws SQLException;
    
}
