public class ClientesPendentesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ClientePendenteAdapter adapter;
    private List<Cliente> clientes = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes_pendentes);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerViewClientesPendentes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new ClientePendenteAdapter(clientes);
        recyclerView.setAdapter(adapter);
        
        carregarClientesComPendencias();
    }

    private void carregarClientesComPendencias() {
        // Primeiro buscamos todas as vendas não pagas
        db.collection("vendas")
            .whereEqualTo("pago", false)
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                List<Venda> vendasPendentes = queryDocumentSnapshots.toObjects(Venda.class);
                
                // Agora buscamos os clientes que têm essas vendas
                Set<String> clientesIds = new HashSet<>();
                for (Venda venda : vendasPendentes) {
                    clientesIds.add(venda.getClienteId());
                }
                
                if (!clientesIds.isEmpty()) {
                    db.collection("clientes")
                        .whereIn(FieldPath.documentId(), new ArrayList<>(clientesIds))
                        .get()
                        .addOnSuccessListener(clientesSnapshot -> {
                            clientes = clientesSnapshot.toObjects(Cliente.class);
                            adapter.setClientes(clientes);
                        });
                }
            });
    }
}
