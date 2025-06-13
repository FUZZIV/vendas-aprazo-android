public class NovaVendaActivity extends AppCompatActivity {
    private EditText etValor;
    private Button btnSalvar;
    private RadioGroup rgVencimento;
    private FirebaseFirestore db;
    private String clienteId;
    private String clienteNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_venda);

        db = FirebaseFirestore.getInstance();
        clienteId = getIntent().getStringExtra("CLIENTE_ID");

        etValor = findViewById(R.id.etValor);
        btnSalvar = findViewById(R.id.btnSalvar);
        rgVencimento = findViewById(R.id.rgVencimento);

        carregarNomeCliente();

        btnSalvar.setOnClickListener(v -> salvarVenda());
    }

    private void carregarNomeCliente() {
        db.collection("clientes").document(clienteId)
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                Cliente cliente = documentSnapshot.toObject(Cliente.class);
                if (cliente != null) {
                    clienteNome = cliente.getNome();
                    setTitle("Nova Venda - " + clienteNome);
                }
            });
    }

    private void salvarVenda() {
        String valorStr = etValor.getText().toString().trim();
        if (valorStr.isEmpty()) {
            etValor.setError("Informe o valor");
            return;
        }

        double valor = Double.parseDouble(valorStr);
        boolean vencimentoDia20 = rgVencimento.getCheckedRadioButtonId() == R.id.rbDia20;

        Venda venda = new Venda();
        venda.setClienteId(clienteId);
        venda.setClienteNome(clienteNome);
        venda.setDataVenda(new Date());
        venda.setValor(valor);
        venda.calcularDataVencimento(vencimentoDia20);

        db.collection("vendas")
            .add(venda)
            .addOnSuccessListener(documentReference -> {
                // Atualizar cliente com a nova venda
                db.collection("clientes").document(clienteId)
                    .update("vendasIds", FieldValue.arrayUnion(documentReference.getId()))
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Venda registrada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
            })
            .addOnFailureListener(e -> {
                Toast.makeText(this, "Erro ao registrar venda", Toast.LENGTH_SHORT).show();
            });
    }
}
