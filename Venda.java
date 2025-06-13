public class Venda {
    private String id;
    private String clienteId;
    private String clienteNome;
    private Date dataVenda;
    private Date dataVencimento;
    private double valor;
    private boolean pago;

    public Venda() {
        pago = false;
    }

    // Getters e setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }
    public String getClienteNome() { return clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }
    public Date getDataVenda() { return dataVenda; }
    public void setDataVenda(Date dataVenda) { this.dataVenda = dataVenda; }
    public Date getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(Date dataVencimento) { this.dataVencimento = dataVencimento; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public boolean isPago() { return pago; }
    public void setPago(boolean pago) { this.pago = pago; }
}
