public class ListaClientesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ClienteAdapter adapter;
    private List<Cliente> clientes = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerViewClientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new ClienteAdapter(clientes, cliente -> {
            // Abrir detalhes do cliente
            Intent intent = new Intent(this, DetalhesClienteActivity.class);
            intent.putExtra("CLIENTE_ID", cliente.getId());
            startActivity(intent);
        });
        
        recyclerView.setAdapter(adapter);
        carregarClientes();
    }

    private void carregarClientes() {
        db.collection("clientes")
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                clientes = queryDocumentSnapshots.toObjects(Cliente.class);
                adapter.setClientes(clientes);
            });
    }

    public void abrirCadastroCliente(View view) {
        startActivity(new Intent(this, CadastroClienteActivity.class));
    }
}
