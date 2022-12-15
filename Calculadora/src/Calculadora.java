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
    JButton limpar = new JButton("Limpar");
    JButton novo = new JButton("Novo");
    JButton calcularAreaCalculos = new JButton("Calcular");
    JButton salvar = new JButton("Salvar");
    JButton imprimir = new JButton("Imprimir");
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
    ArrayList <String> listDosTextosQueSeraoAdicionadosNoTextArea = new ArrayList<>();
    ArrayList <String> listDosTextosDoMostrarCalculoFinal = new ArrayList<>();

    protected Calculadora (){

        // Terminar as bordas //

        try{

            Color corDeFundo = new Color(19,114,218,255);
            Color objetos = new Color(198, 235, 255,255);
            Font fonte = new Font("Arial", Font.PLAIN, 12);
            LineBorder bordas = (LineBorder) BorderFactory.createLineBorder(objetos, 5, true);

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

            labelDataInicial.setFont(fonte);
            labelDataFinal.setFont(fonte);

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

            limpar.addActionListener(this);
            limpar.addMouseListener(getMouseEvent());
            limpar.getCursor();
            limpar.setToolTipText("Limpa as caixas de data, caixa de texto e area de anotações.");
            limpar.setBackground(Color.WHITE);

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

            imprimir.setToolTipText("Imprime ");
            imprimir.setBackground(Color.WHITE);

            limparAreaCalculos.addMouseListener(getMouseEvent());
            limparAreaCalculos.addActionListener(this);
            limparAreaCalculos.setBackground(Color.WHITE);

            JPanel painelDaAreaPrincipal = new JPanel(new GridLayout(2,4,5,5));
            painelDaAreaPrincipal.setBounds(0,120,325,80);
            painelDaAreaPrincipal.setBackground(corDeFundo);
            painelDaAreaPrincipal.add(labelDataInicial);
            painelDaAreaPrincipal.add(dataInicial);
            painelDaAreaPrincipal.add(calcular);
            painelDaAreaPrincipal.add(desfazer);
            painelDaAreaPrincipal.add(labelDataFinal);
            painelDaAreaPrincipal.add(dataFinal);
            painelDaAreaPrincipal.add(limpar);
            painelDaAreaPrincipal.add(novo);

            JPanel painelDosBotoes = new JPanel(new GridLayout(1,4,5,5));
            painelDosBotoes.setBackground(corDeFundo);
            painelDosBotoes.setBounds(330,400,325,40);
            painelDosBotoes.add(calcularAreaCalculos);
            painelDosBotoes.add(salvar);
            painelDosBotoes.add(imprimir);
            painelDosBotoes.add(limparAreaCalculos);

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

        if(e.getSource() == calcular && dataInicial.getValue() != null && dataInicial.getValue() != "" && dataFinal.getValue() != null && dataFinal.getValue() != ""){

            boolean datasSaoInvalidas = datasSaoInvalidas();
            if (!datasSaoInvalidas) {
                CalculaTempo(areaCalculos, dataInicial, dataFinal);
            }

        }

        if(e.getSource() == desfazer){
            desfazerCalculos( listDoTempoDeTrabalho, listDeStringsDasSomasDiasEntreDuasDatas);
            Textos.desfazerTextos(areaCalculos, listDosTextosQueSeraoAdicionadosNoTextArea);
        }

        if(e.getSource() == limpar){
            Textos.limpar(nomeIndividuo, areaAnotacoes, dataInicial, dataFinal);
        }

        if(e.getSource() == novo){
            new Calculadora();
        }

        if(e.getSource() == calcularAreaCalculos){
            Textos.MostraCalculoFinal(areaCalculos, tempoDeTrabalho, somaDiasEntreDuasDatas, listDosTextosDoMostrarCalculoFinal);
        }

        if(e.getSource() == salvar) Textos.salvarCalculos(nomeIndividuo, areaCalculos, tempoDeTrabalho, somaDiasEntreDuasDatas, listDosTextosDoMostrarCalculoFinal);

        if(e.getSource() == imprimir){
            Textos.imprimirCalculos(nomeIndividuo, areaCalculos, tempoDeTrabalho, somaDiasEntreDuasDatas, listDosTextosDoMostrarCalculoFinal);
        }

        if(e.getSource() == limparAreaCalculos){
            Textos.limparAreaCalculos(areaCalculos, listDosTextosQueSeraoAdicionadosNoTextArea, listDoTempoDeTrabalho, listDeStringsDasSomasDiasEntreDuasDatas);
            limparCalculos();
        }
    }

    private boolean datasSaoInvalidas(){

        try {

            String textoDataInicial = dataInicial.getText();
            String textoDataFinal = dataFinal.getText();

            int diaDataInicial = Integer.parseInt(textoDataInicial.substring(0, 2));
            int mesDataInicial = Integer.parseInt(textoDataInicial.substring(3, 5));
            int anoDataInicial = Integer.parseInt(textoDataInicial.substring(6, 10));
            int diaDataFinal = Integer.parseInt(textoDataFinal.substring(0, 2));
            int mesDataFinal = Integer.parseInt(textoDataFinal.substring(3, 5));
            int anoDataFinal = Integer.parseInt(textoDataFinal.substring(6, 10));

            if (0 >= diaDataInicial || diaDataInicial > 31 && (mesDataInicial == 1 || mesDataInicial == 3 || mesDataInicial == 5 || mesDataInicial == 7 || mesDataInicial == 8 || mesDataInicial == 10 || mesDataInicial == 12) || diaDataInicial > 30 && (mesDataInicial == 2 || mesDataInicial == 4 || mesDataInicial == 6 || mesDataInicial == 9 || mesDataInicial == 11) || diaDataInicial > 28 && mesDataInicial == 2 && anoDataInicial % 4 != 0 || diaDataInicial > 29 && mesDataInicial == 2) {
                areaCalculos.append("A data inicial é inválida! \n\n");
                return true;
            }
            if (0 >= diaDataFinal || diaDataFinal > 31 && (mesDataFinal == 1 || mesDataFinal == 3 || mesDataFinal == 5 || mesDataFinal == 7 || mesDataFinal == 8 || mesDataFinal == 10 || mesDataFinal == 12) || diaDataFinal > 30 && (mesDataFinal == 2 || mesDataFinal == 4 || mesDataFinal == 6 || mesDataFinal == 9 || mesDataFinal == 11) || diaDataFinal > 28 && mesDataFinal == 2 && anoDataFinal % 4 != 0 || diaDataFinal > 29 && mesDataFinal == 2) {
                areaCalculos.append("A data final é inválida! \n\n");
                return true;
            }
        }
        catch (Exception ex){
            areaCalculos.append("Ocorreu o erro: " + (ex) + " Por favor, verifique se as datas estão corretas. \n\n");
        }
        return false;
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

                if(e.getSource() == limpar){
                    limpar.setText("Certeza?");
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

                if(e.getSource() == limpar){
                    limpar.setText("Limpar");
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

    protected void CalculaTempo(JTextArea areaCalculos, JTextField dataInicial, JTextField dataFinal){

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
                    "\n" + "De " + localDataInicial.getDayOfMonth() + "/" + localDataInicial.getMonthValue()+ "/" + localDataInicial.getYear() + " até " +
                    localDataFinal.getDayOfMonth() + "/" + localDataFinal.getMonthValue() + "/" + localDataFinal.getYear() +
                    "\n" + "Duração: " +periodoDeTrabalho.getYears() + " Anos " +  periodoDeTrabalho.getMonths() + " Meses " + periodoDeTrabalho.getDays() + " e Dias" +
                    "\n" + "Duração em Dias: " + diasEntreDuasDatas + "\n \n";

            listDosTextosQueSeraoAdicionadosNoTextArea.add(textoQueSeraAdicionadoNoTextArea);

            areaCalculos.append(textoQueSeraAdicionadoNoTextArea);
            Textos.limpar(this.dataInicial, this.dataFinal);

        }
    }

    private void desfazerCalculos(ArrayList <Period> listDoTempoDeTrabalho, ArrayList <String> listDeStringsDasSomasDiasEntreDuasDatas){

        int ultimaPosicaoDasLists = listDeStringsDasSomasDiasEntreDuasDatas.size() - 1;

        if(listDeStringsDasSomasDiasEntreDuasDatas.size() > 0){
            if(ultimaPosicaoDasLists > 0){
                somaDiasEntreDuasDatas = Long.parseLong(listDeStringsDasSomasDiasEntreDuasDatas.get(ultimaPosicaoDasLists));
            }
            else{
                somaDiasEntreDuasDatas = 0;
            }
            listDeStringsDasSomasDiasEntreDuasDatas.remove(ultimaPosicaoDasLists);
        }

        if(listDoTempoDeTrabalho.size() > 0){
            if(ultimaPosicaoDasLists > 0){
                tempoDeTrabalho = listDoTempoDeTrabalho.get(ultimaPosicaoDasLists);
            }
            else{
                tempoDeTrabalho = Period.parse("P0Y0M0D");
            }
            listDoTempoDeTrabalho.remove(ultimaPosicaoDasLists);
        }

        if(ordemDosPeriodos > 0){
            --ordemDosPeriodos;
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