# JogoDaVelha-MVC
Versão MVC.<br>
Explora <strong>técnica hook class</strong> para estender comportamento do Jogo da Velha.<br>
Através de um menu, usuário seleciona uma das versões :

<br><strong> computador versus computador</strong>
<br><strong> humano versus computador</strong>
<br><strong> humano versus humano</strong><br>

Usa análise combinatória para calcular jogadas ou cria as jogadas aleatoriamente. <br>
Joga-se contra o computador ou entre duas pessoas ou computador versus computador. <br>
Todas essas possibilidades setadas através do menu JOGOS.<br>

FICA CLARO O PODER DO DESACOPLAMENTO ENTRE VIEW E MODEL, estabelecido no <strong>padrao de projeto</strong> MVC. <br>
O JOGO DA VELHA, O MODELO, NAO SABE O TIPO DE SEUS CLIENTES : <br>
PODE SER UMA JFrame DO Swing OU MESMO <strong>UMA MOCK VIEW</strong>, CRIADA NOS TESTES UNITÁRIOS.<br>
COM ISSO, FICA PROVADO QUE PODEMOS TER IMPLEMENTADOS CLIENTES WEB, OU MESMO OUTRA VIEW DESKTOP, IMPLEMENTADA USANDO TECNOLOGIA Java FX.
