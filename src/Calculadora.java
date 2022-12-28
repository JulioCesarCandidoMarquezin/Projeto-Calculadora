import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Calculadora extends JFrame implements ActionListener {

    JFormattedTextField dataInicial, dataFinal;

    JTextField nomeIndividuo = new JTextField("Nome da pessoa");

    JButton calcular = new JButton("Calcular");
    JButton desfazer = new JButton("Desfazer");
    JButton novo = new JButton("Novo");
    JButton calcularAreaCalculos = new JButton("Calcular");
    JButton salvar = new JButton("Salvar");
    JButton limparAreaCalculos = new JButton("Limpar");

    JTextArea areaAnotacoes = new JTextArea("Anotações");
    JTextArea areaCalculos = new JTextArea("");

    Period tempoDeTrabalho = Period.parse("P0Y0M0D");
    Period periodoDeTrabalho;

    long somaDiasEntreDuasDatas = 0;
    int ordemDosPeriodos = 0;

    String textoQueSeraAdicionadoNoTextArea;

    ArrayList <Period> listDoTempoDeTrabalho = new ArrayList<>();
    ArrayList <String> listDeStringsDasSomasDiasEntreDuasDatas = new ArrayList<>();
    ArrayList <String> listDosTextosQueSeraoAdicionadosNoTextAreaPeloCalcular = new ArrayList<>();
    ArrayList <String> listDosTextosDoMostrarCalculoFinal = new ArrayList<>();

    protected Calculadora (){

        // Terminar as bordas //

        try{

            Color corDeFundo = new Color(19,114,218,255);
            Color objetos = new Color(198, 235, 255,255);
            Font fonte = new Font("Arial", Font.PLAIN, 12);
            LineBorder bordas = (LineBorder) BorderFactory.createLineBorder(objetos, 5);

            setIconImage(Toolkit.getDefaultToolkit().getImage("rondonia.png"));

            getContentPane().setBackground(corDeFundo);

            ImageIcon seduc = new ImageIcon("seduc.png");
            JLabel imagemSeduc = new JLabel(seduc);
            imagemSeduc.setBounds(0,0,325,70);

            JScrollPane scrollDoAreaCalculos = new JScrollPane(areaCalculos);
            JScrollPane scrollDasAnotacoes = new JScrollPane(areaAnotacoes);

            dataInicial = new JFormattedTextField(new MaskFormatter("##/##/####"));
            dataInicial.setBorder(bordas);
            dataInicial.setBounds(80, 130, 80, 40);
            dataInicial.setToolTipText("Data inicial de um periodo.");
            dataInicial.setFont(fonte);

            dataFinal = new JFormattedTextField(new MaskFormatter("##/##/####"));
            dataFinal.setBorder(bordas);
            dataFinal.setToolTipText("Data final de um periodo.");
            dataFinal.setFont(fonte);

            JLabel labelNomeDoIndividuo = new JLabel("Nome", JLabel.CENTER);
            JLabel labelDataInicial = new JLabel("Data Inicial", JLabel.CENTER);
            JLabel labelDataFinal = new JLabel("Data Final", JLabel.CENTER);
            JLabel labelAreaAnotacoes = new JLabel("Anotações", JLabel.CENTER);
            JLabel labelAreaCalculos = new JLabel("Calculos", JLabel.CENTER);

            labelDataInicial.setFont(new Font("Arial",Font.PLAIN, 16));
            labelDataInicial.setForeground(objetos);

            labelDataFinal.setFont(new Font("Arial",Font.PLAIN, 16));
            labelDataFinal.setForeground(objetos);

            nomeIndividuo.setBorder(bordas);
            nomeIndividuo.setBounds(0, 70, 325, 40);
            nomeIndividuo.setToolTipText("Nome da pessoa ao qual está lidando, serve para se organizar caso esteja lidando com multiplas pessoas ao mesmo tempo.");
            nomeIndividuo.setFont(fonte);
            nomeIndividuo.addMouseListener(getMouseEvent());

            calcular.addActionListener(this);
            calcular.setToolTipText("Calcula o periodo entre as datas especificadas");
            calcular.setBackground(Color.WHITE);

            desfazer.addActionListener(this);
            desfazer.addMouseListener(getMouseEvent());
            desfazer.setToolTipText("Desfaz o ultimo cálculo realizado");
            desfazer.getCursor();
            desfazer.setBackground(Color.WHITE);

            novo.addActionListener(this);
            novo.setToolTipText("Cria uma nova janela.");
            novo.setBackground(Color.WHITE);

            areaAnotacoes.setLineWrap(true);
            areaAnotacoes.setWrapStyleWord(true);
            areaAnotacoes.setFont(fonte);
            areaAnotacoes.addMouseListener(getMouseEvent());

            scrollDasAnotacoes.setBounds(0, 210, 325, 230);
            scrollDasAnotacoes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollDasAnotacoes.setBorder(bordas);

            areaCalculos.setLineWrap(true);
            areaCalculos.setWrapStyleWord(true);
            areaCalculos.setEditable(false);
            areaCalculos.setText("");
            areaCalculos.setFont(fonte);

            scrollDoAreaCalculos.setBounds(330, 0, 325, 400);
            scrollDoAreaCalculos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollDoAreaCalculos.setBorder(bordas);
            scrollDoAreaCalculos.add(labelAreaCalculos);

            calcularAreaCalculos.addActionListener(this);
            calcularAreaCalculos.setToolTipText("Mostra o tempo total considerando todos os calculos realizados");
            calcularAreaCalculos.setBackground(Color.WHITE);

            salvar.addActionListener(this);
            salvar.setToolTipText("Salva o texto dos calculos em um arquivo na pasta especificada");
            salvar.setBackground(Color.WHITE);

            limparAreaCalculos.addMouseListener(getMouseEvent());
            limparAreaCalculos.addActionListener(this);
            limparAreaCalculos.setBackground(Color.WHITE);

            JPanel painelDaAreaPrincipal = new JPanel(new GridLayout(2,3,5,5));
            painelDaAreaPrincipal.setBounds(0,120,325,80);
            painelDaAreaPrincipal.setBackground(corDeFundo);
            painelDaAreaPrincipal.add(labelDataInicial);
            painelDaAreaPrincipal.add(dataInicial);
            painelDaAreaPrincipal.add(calcular);
            painelDaAreaPrincipal.add(labelDataFinal);
            painelDaAreaPrincipal.add(dataFinal);
            painelDaAreaPrincipal.add(desfazer);

            JPanel painelDosBotoes = new JPanel(new GridLayout(1,4,5,5));
            painelDosBotoes.setBackground(corDeFundo);
            painelDosBotoes.setBounds(330,400,325,40);
            painelDosBotoes.add(calcularAreaCalculos);
            painelDosBotoes.add(salvar);
            painelDosBotoes.add(limparAreaCalculos);
            painelDosBotoes.add(novo);

            add(painelDaAreaPrincipal);
            add(painelDosBotoes);
            add(imagemSeduc);
            add(labelNomeDoIndividuo);
            add(labelAreaAnotacoes);
            add(labelAreaCalculos);
            add(nomeIndividuo);
            add(scrollDasAnotacoes);
            add(scrollDoAreaCalculos);

            setTitle("Tempo de Contribuição");
            setSize(675, 480);
            setLayout(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
        }
        catch(ParseException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == calcular){

            try{
                CalculaTempo();
            }
            catch (Exception ex){
                areaCalculos.append("Data Inválida\n\n");
            }
        }

        if(e.getSource() == desfazer){
            desfazerCalculos();
            Textos.desfazerTextos(areaCalculos, listDosTextosQueSeraoAdicionadosNoTextAreaPeloCalcular);
        }

        if(e.getSource() == novo){
            new Calculadora();
        }

        if(e.getSource() == calcularAreaCalculos){
            Textos.MostraCalculoFinal(areaCalculos, tempoDeTrabalho, somaDiasEntreDuasDatas, listDosTextosDoMostrarCalculoFinal);
        }

        if(e.getSource() == salvar) Textos.salvarCalculos(nomeIndividuo, areaCalculos, tempoDeTrabalho, somaDiasEntreDuasDatas, listDosTextosDoMostrarCalculoFinal);

        if(e.getSource() == limparAreaCalculos){
            Textos.limparTudo(nomeIndividuo, areaAnotacoes, dataInicial, dataFinal, areaCalculos, listDosTextosQueSeraoAdicionadosNoTextAreaPeloCalcular, listDoTempoDeTrabalho, listDeStringsDasSomasDiasEntreDuasDatas);
            limparCalculos();
        }
    }

    private MouseAdapter getMouseEvent() {

        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                if(e.getSource() == nomeIndividuo && nomeIndividuo.getText().equals("Nome da pessoa")){
                    nomeIndividuo.setText("");
                }

                if(e.getSource() == areaAnotacoes && areaAnotacoes.getText().equals("Anotações")){
                    areaAnotacoes.setText("");
                }

                if(e.getSource() == desfazer){
                    desfazer.setText("Certeza?");
                }

                if(e.getSource() == limparAreaCalculos){
                    limparAreaCalculos.setText("Certeza?");
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {

                if(e.getSource() == nomeIndividuo && nomeIndividuo.getText().equals("")){
                    nomeIndividuo.setText("Nome da pessoa");
                }

                if(e.getSource() == areaAnotacoes && areaAnotacoes.getText().equals("")){
                    areaAnotacoes.setText("Anotações");
                }

                if(e.getSource() == desfazer){
                    desfazer.setText("Desfazer");
                }

                if(e.getSource() == limparAreaCalculos){
                    limparAreaCalculos.setText("Limpar");
                }

            }
        };
    }

    protected void CalculaTempo(){

        DateTimeFormatter formatoDosLocalDates = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate localDataInicial = LocalDate.parse(dataInicial.getText(), formatoDosLocalDates);
        LocalDate localDataFinal = LocalDate.parse(dataFinal.getText(), formatoDosLocalDates);
        LocalDate localDataFinalSomadoComTempoDeTrabalho = localDataFinal.plus(tempoDeTrabalho);

        periodoDeTrabalho = Period.between(localDataInicial, localDataFinal);
        boolean periodoNegativo = periodoDeTrabalho.isNegative();

        if(periodoNegativo){
            areaCalculos.append("A data inicial é maior que a data final \n\n");
        }
        else{
            long diasEntreDuasDatas = ChronoUnit.DAYS.between(localDataInicial, localDataFinal);

            tempoDeTrabalho = Period.between(localDataInicial, localDataFinalSomadoComTempoDeTrabalho);
            tempoDeTrabalho = tempoDeTrabalho.normalized();
            listDoTempoDeTrabalho.add(tempoDeTrabalho);

            somaDiasEntreDuasDatas = somaDiasEntreDuasDatas + diasEntreDuasDatas;
            listDeStringsDasSomasDiasEntreDuasDatas.add(String.valueOf(somaDiasEntreDuasDatas));

            ++ordemDosPeriodos;

            textoQueSeraAdicionadoNoTextArea = ordemDosPeriodos + "° Periodo" +
                    "\n" + "De " + localDataInicial.format(formatoDosLocalDates) + " até " + localDataFinal.format(formatoDosLocalDates) +
                    "\n" + "Duração: " +periodoDeTrabalho.getYears() + " Anos " +  periodoDeTrabalho.getMonths() + " Meses " + periodoDeTrabalho.getDays() + " e Dias" +
                    "\n" + "Duração em Dias: " + diasEntreDuasDatas + "\n \n";

            listDosTextosQueSeraoAdicionadosNoTextAreaPeloCalcular.add(textoQueSeraAdicionadoNoTextArea);

            areaCalculos.append(textoQueSeraAdicionadoNoTextArea);
            Textos.limparDatas(this.dataInicial, this.dataFinal);
        }
    }

    private void desfazerCalculos(){

        if(listDeStringsDasSomasDiasEntreDuasDatas.size() > 1){

            somaDiasEntreDuasDatas = Long.parseLong(listDeStringsDasSomasDiasEntreDuasDatas.get(listDeStringsDasSomasDiasEntreDuasDatas.size() - 2));
            listDeStringsDasSomasDiasEntreDuasDatas.remove(listDeStringsDasSomasDiasEntreDuasDatas.size() - 1);
        }
        else{
            somaDiasEntreDuasDatas = 0;
        }

        if(listDoTempoDeTrabalho.size() > 1){

            tempoDeTrabalho = listDoTempoDeTrabalho.get(listDoTempoDeTrabalho.size() - 2);
            listDoTempoDeTrabalho.remove(listDoTempoDeTrabalho.size() - 1);
        }
        else{
            tempoDeTrabalho = Period.parse("P0Y0M0D");
        }

        if(ordemDosPeriodos > 0){
            --ordemDosPeriodos;
        }

        if(listDosTextosQueSeraoAdicionadosNoTextAreaPeloCalcular.size() > 0) {
            String textoQueSeraTiradoDoTextArea = listDosTextosDoMostrarCalculoFinal.get(listDosTextosDoMostrarCalculoFinal.size() - 1);
            String novoTextoDoTextArea = areaCalculos.getText().replace(textoQueSeraTiradoDoTextArea, "");
            areaCalculos.setText(novoTextoDoTextArea);
            listDosTextosDoMostrarCalculoFinal.remove(textoQueSeraTiradoDoTextArea);
        }
    }

    private void limparCalculos(){
        tempoDeTrabalho = Period.parse("P0Y0M0D");
        periodoDeTrabalho = Period.parse("P0Y0M0D");
        somaDiasEntreDuasDatas = 0;
        ordemDosPeriodos = 0;
    }

    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex1) {
            java.util.logging.Logger.getLogger(Calculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex1);
        }

        new Calculadora();
    }
}