public class Cliente {
    private String id;
    private String nome;
    private String telefone;
    private String endereco;
    private List<String> vendasIds; // IDs das vendas associadas

    public Cliente() {
        vendasIds = new ArrayList<>();
    }

    // Getters e setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public List<String> getVendasIds() { return vendasIds; }
    public void addVenda(String vendaId) { this.vendasIds.add(vendaId); }
}
