package sf.net.dvstar.diadiary.database;

public interface CommonItem {

    String FIELD_DELIMITER = "\\|";

    /**
     * Get simle list item
     * @return item string
     */
    public String getListText();

    /**
     * Export item to text file
     * @return exported string
     */
    public String exportItem();

    /**
     * Import item from text file
     * @param item string to parse
     */
    public void importItem(String item);


}
