public class VendaAdapter extends RecyclerView.Adapter<VendaAdapter.VendaViewHolder> {
    private List<Venda> vendas;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public VendaAdapter(List<Venda> vendas) {
        this.vendas = vendas;
    }

    @NonNull
    @Override
    public VendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_venda, parent, false);
        return new VendaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendaViewHolder holder, int position) {
        Venda venda = vendas.get(position);
        holder.tvData.setText(dateFormat.format(venda.getDataVenda()));
        holder.tvValor.setText(String.format(Locale.getDefault(), "R$ %.2f", venda.getValor()));
        holder.tvStatus.setText(venda.isPago() ? "Pago" : "Pendente");
        holder.tvStatus.setTextColor(venda.isPago() ? 
                holder.itemView.getContext().getColor(android.R.color.holo_green_dark) : 
                holder.itemView.getContext().getColor(android.R.color.holo_red_dark));
    }

    @Override
    public int getItemCount() {
        return vendas.size();
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
        notifyDataSetChanged();
    }

    static class VendaViewHolder extends RecyclerView.ViewHolder {
        TextView tvData, tvValor, tvStatus;

        public VendaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tvDataVenda);
            tvValor = itemView.findViewById(R.id.tvValorVenda);
            tvStatus = itemView.findViewById(R.id.tvStatusVenda);
        }
    }
}
