import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 
 * FLUXO PARA TESTAR A IMPLEMENTAÇÃO:
 * SÓ PARA MOSTRAR QUE SE INCLUSO UM MERCADO, POSSIBILITARIA
 * COMPRAR COM PAGAMENTO (ISSO NÃO FOI INCLUSO NA MODELAGEM
 * MAS É UM ASPECTO DA SOLUÇÃO QUE DEMONSTRA O FUNCIONAMENTO
 * EM TESTES)
*/
public class Mercado {
	private final List<Venda> vendas;
	private final Map<Venda, Double> pagamentos;

	public Mercado() {
		this.vendas = new ArrayList<>();
		this.pagamentos = new HashMap<>();
	}

	public Venda criarVenda(Cliente cliente, Map<Produto, ItemVenda> itens) {
		Venda venda = new Venda(cliente, itens);
		vendas.add(venda);
		pagamentos.put(venda, 0.0);
		return venda;
	}

	public boolean pagar(Venda venda, double valor) {
		if (!vendas.contains(venda) || valor <= 0) {
			return false;
		}

		double totalPago = pagamentos.get(venda) + valor;
		pagamentos.put(venda, totalPago);

		if (totalPago >= venda.getTotal()) {
			vendas.remove(venda);
			pagamentos.remove(venda);
		}

		return true;
	}

	public List<Venda> getVendas() {
		return new ArrayList<>(vendas);
	}

	public double getTotalPago(Venda venda) {
		return pagamentos.getOrDefault(venda, 0.0);
	}

    public static void main(String[] args) {
        Mercado mercado = new Mercado();
        Cliente cliente = new Cliente(Perfil.STANDARD, "Ana");
        Produto produto = new Produto(1, "Caderno", 10.0);
        
        Map<Produto, ItemVenda> itens = new HashMap<>();
        itens.put(produto, new ItemVenda(produto, 2));

        Venda venda = mercado.criarVenda(cliente, itens);
        double total = venda.getTotal();

        boolean primeiraMetade = mercado.pagar(venda, total / 2);
        if (! (primeiraMetade && mercado.getVendas().contains(venda))) {
            throw new AssertionError("A venda nao deveria ser removida antes do pagamento total");
        }

        boolean segundaMetade = mercado.pagar(venda, total / 2);
        if (! (segundaMetade && mercado.getVendas().isEmpty())) {
            throw new AssertionError("A venda deveria ser removida apos o pagamento total");
        }

        System.out.println("Compra paga com sucesso: R$ " + total);
    }
}

