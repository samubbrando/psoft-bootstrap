public class ItemVenda {
    private Produto produto;
    private int quantidade;

    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        double preco = this.produto.getPreco();
        if (quantidade > 10) {
            preco = this.aplicaDesconto(preco);
        }
        return preco * quantidade;
    }

    private double aplicaDesconto(double valor) {
        return valor * 0.8;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

}