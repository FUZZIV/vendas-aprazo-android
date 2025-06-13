public class ClientePendenteAdapter extends RecyclerView.Adapter<ClientePendenteAdapter.ClientePendenteViewHolder> {
    private List<Cliente> clientes;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ClientePendenteAdapter(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @NonNull
    @Override
    public ClientePendenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente_pendente, parent, false);
        return new ClientePendenteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientePendenteViewHolder holder, int position) {
        Cliente cliente = clientes.get(position);
        holder.tvNome.setText(cliente.getNome());
        
        // Buscar valor total pendente
        db.collection("vendas")
            .whereEqualTo("clienteId", cliente.getId())
            .whereEqualTo("pago", false)
            .get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                double totalPendente = 0;
                for (DocumentSnapshot doc : queryDocumentSnapshots) {
                    Venda venda = doc.toObject(Venda.class);
                    if (venda != null) {
                        totalPendente += venda.getValor();
                    }
                }
                holder.tvValorPendente.setText(String.format(Locale.getDefault(), "R$ %.2f", totalPendente));
            });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
        notifyDataSetChanged();
    }

    static class ClientePendenteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvValorPendente;

        public ClientePendenteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNomeClientePendente);
            tvValorPendente = itemView.findViewById(R.id.tvValorPendente);
        }
    }
}
