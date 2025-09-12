package br.com.imbazzu.listatelefonicacjs.repository;

import br.com.imbazzu.listatelefonicacjs.entity.Empresa;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class EmpresaSpecification {


    public static Specification<Empresa> buscarPorTermo(String termo) {
        return (root, query, cb) ->{
          if(termo == null || termo.isEmpty()) {
              return cb.conjunction();
            }
            query.distinct(true);

            var likeTermo = "%" + termo.toLowerCase() + "%";

            var nomePredicato = cb.like(cb.lower(root.get("nome")), likeTermo);
            Join<Object,Object> categoriaJoin = root.join("categorias", JoinType.LEFT);

            var categoriaPredicato = cb.like(cb.lower(categoriaJoin.get("nome")), likeTermo);

            var descricaoPredicato = cb.like(cb.lower(root.get("descricao")), likeTermo);

            var telefoneJoin = root.join("telefones", JoinType.LEFT);
            var telefonePredicato = cb.like(cb.lower(telefoneJoin.get("numero")), likeTermo);

            var bairroPredicato = cb.like(cb.lower(root.get("endereco").get("bairro")), likeTermo);
            var endNumeroPredicato = cb.like(cb.lower(root.get("endereco").get("numero")), likeTermo);
            var endLogradouroPredicado= cb.like(cb.lower(root.get("endereco").get("logradouro")), likeTermo);
            var cidadePredicato = cb.like(cb.lower(root.get("endereco").get("cidade")), likeTermo);
            var ufPredicato= cb.like(cb.lower(root.get("endereco").get("uf")), likeTermo);


            return cb.or(nomePredicato,categoriaPredicato,telefonePredicato,
                    endNumeroPredicato, bairroPredicato, endLogradouroPredicado,
                    cidadePredicato, ufPredicato,descricaoPredicato);
        } ;
    }
}
