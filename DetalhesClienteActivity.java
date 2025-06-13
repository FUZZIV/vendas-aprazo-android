public class DetalhesClienteActivity extends AppCompatActivity {
    private TextView tvNome, tvTelefone, tvEndereco;
    private RecyclerView recyclerViewVendas;
    private Button btnNovaVenda;
    private VendaAdapter adapter;
    private List<Venda> vendas = new ArrayList<>();
    private FirebaseFirestore db;
    private String clienteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_cliente);

        db = FirebaseFirestore.getInstance();
        clienteId = getIntent().getStringExtra("CLIENTE_ID");

        tvNome = findViewById(R.id.tvNome);
        tvTelefone = findViewById(R.id.tvTelefone);
        tvEndereco = findViewById(R.id.tvEndereco);
        recyclerViewVendas = findViewById(R.id.recyclerViewVendas);
        btnNovaVenda = findViewById(R.id.btnNovaVenda);

        recyclerViewVendas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VendaAdapter(vendas);
        recyclerViewVendas.setAdapter(adapter);

        btnNovaVenda.setOnClickListener(v -> {
            Intent intent = new Intent(this, NovaVendaActivity.class);
            intent.putExtra("CLIENTE_ID", clienteId);
            startActivity(intent);
        });

        carregarDadosCliente();
        carregarVendas();
    }

    private void carregarDadosCliente() {
        db.collection("clientes").document(clienteId)
            .get()
            .addOnSuccessListener(documentSnapshot -> {
                Cliente cliente = documentSnapshot.toObject(Cliente.class);
                if (cliente != null) {
                    tvNome.setText(cliente.getNome());
                    tvTelefone.setText(cliente.getTelefone());
                    tvEndereco.setText(cliente.getEndereco());
                }
            });
    }

    private void carregarVendas() {
        db.collection("vendas")
            .whereEqualTo("clienteId", clienteId)
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                vendas = queryDocumentSnapshots.toObjects(Venda.class);
                adapter.setVendas(vendas);
            });
    }
}
