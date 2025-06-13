public class CadastroClienteActivity extends AppCompatActivity {
    private EditText etNome, etTelefone, etEndereco;
    private Button btnSalvar;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        db = FirebaseFirestore.getInstance();

        etNome = findViewById(R.id.etNome);
        etTelefone = findViewById(R.id.etTelefone);
        etEndereco = findViewById(R.id.etEndereco);
        btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(v -> salvarCliente());
    }

    private void salvarCliente() {
        String nome = etNome.getText().toString().trim();
        String telefone = etTelefone.getText().toString().trim();
        String endereco = etEndereco.getText().toString().trim();

        if (nome.isEmpty()) {
            etNome.setError("Nome é obrigatório");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);

        db.collection("clientes")
            .add(cliente)
            .addOnSuccessListener(documentReference -> {
                Toast.makeText(this, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            })
            .addOnFailureListener(e -> {
                Toast.makeText(this, "Erro ao cadastrar cliente", Toast.LENGTH_SHORT).show();
            });
    }
}
