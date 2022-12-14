import javax.swing.*;
import javax.swing.border.TitledBorder;
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

    Color objetos = new Color(198, 235, 255,255);

    JPanel painel = new JPanel();

    JFormattedTextField dataInicial, dataFinal;

    JTextField nomeIndividuo = new JTextField("");

    JButton calcular = new JButton("Calcular");
    JButton desfazer = new JButton("Desfazer");
    JButton limpar = new JButton("Limpar");
    JButton novo = new JButton("Novo");
    JButton calcularAreaCalculos = new JButton("Calcular");
    JButton salvar = new JButton("Salvar");
    JButton imprimir = new JButton("Imprimir");
    JButton limparAreaCalculos = new JButton("Limpar");

    JTextArea area = new JTextArea("");
    JTextArea areaCalculos = new JTextArea("");

    JScrollPane scroll = new JScrollPane(areaCalculos);
    JScrollPane scroll1 = new JScrollPane(area);

    Period tempoDeTrabalho = Period.parse("P0Y0M0D");
    Period periodoDeTrabalho;

    DateTimeFormatter formatoDosLocalDates = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate localDataInicial, localDataFinal, localDataFinalSomadoComTempoDeTrabalho;

    long somaDiasEntreDuasDatas = 0, diasEntreDuasDatas = 0;
    int ordemDosPeriodos = 0;

    ArrayList <Period> listDoTempoDeTrabalho = new ArrayList<>();
    ArrayList <String> listDeStringsDasSomasDiasEntreDuasDatas = new ArrayList<>();
    ArrayList <String> listDosTextosQueSeraoAdicionadosNoTextArea = new ArrayList<>();

    protected Calculadora (){

        try{

            painel.setSize(700, 550);
            painel.setLayout(null);
            painel.setBackground(new Color(19,114,218,255));
            add(painel);

            dataInicial = new JFormattedTextField(new MaskFormatter("##/##/####"));
            dataFinal = new JFormattedTextField(new MaskFormatter("##/##/####"));

            TitledBorder titulo_Nome_Individuo = BorderFactory.createTitledBorder( "Nome");
            titulo_Nome_Individuo.setTitleJustification(TitledBorder.CENTER);
            nomeIndividuo.setBorder(titulo_Nome_Individuo);
            nomeIndividuo.setBounds(10, 70, 325, 55);
            nomeIndividuo.setFont(new Font("Arial", Font.PLAIN, 12));
            nomeIndividuo.setBackground(objetos);
            nomeIndividuo.setToolTipText("Nome da pessoa ao qual está lidando, serve para se organizar caso esteja lidando com multiplas pessoas ao mesmo tempo.");

            TitledBorder titulo_Data_Inicial = BorderFactory.createTitledBorder("Data Inicial");
            titulo_Data_Inicial.setTitleJustification(TitledBorder.CENTER);
            dataInicial.setBorder(titulo_Data_Inicial);
            dataInicial.setBounds(10, 130, 160, 55);
            dataInicial.setBackground(objetos);
            dataInicial.setToolTipText("Data inicial de um periodo.");

            TitledBorder titulo_Data_Final = BorderFactory.createTitledBorder("Data Final");
            titulo_Data_Final.setTitleJustification(TitledBorder.CENTER);
            dataFinal.setBorder(titulo_Data_Final);
            dataFinal.setBounds(175, 130, 160, 55);
            dataFinal.setBackground(objetos);
            dataFinal.setToolTipText("Data final de um periodo.");

            calcular.setBounds(12, 200, 80, 30);
            calcular.addActionListener(this);

            desfazer.setBounds(92, 200, 80, 30);
            desfazer.addActionListener(this);
            desfazer.addMouseListener(getMouseEvent());
            desfazer.getCursor();

            limpar.setBounds(172, 200, 80, 30);
            limpar.addActionListener(this);
            limpar.addMouseListener(getMouseEvent());
            limpar.getCursor();
            limpar.setToolTipText("Limpa as caixas de data, caixa de texto e area de anotações.");

            novo.setBounds(252, 200, 80, 30);
            novo.addActionListener(this);
            novo.setToolTipText("Cria uma nova guia.");

            TitledBorder titulo_Area = BorderFactory.createTitledBorder("Anotações");
            titulo_Area.setTitleJustification(TitledBorder.CENTER);
            area.setBorder(titulo_Area);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setFont(new Font("Arial", Font.PLAIN, 12));
            area.setBackground(objetos);

            scroll1.setBounds(10, 240, 325, 200);
            scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            TitledBorder titulo_Area_Calculos = BorderFactory.createTitledBorder("Calculos");
            titulo_Area_Calculos.setTitleJustification(TitledBorder.CENTER);
            areaCalculos.setBorder(titulo_Area_Calculos);
            areaCalculos.setLineWrap(true);
            areaCalculos.setWrapStyleWord(true);
            areaCalculos.setEditable(false);
            areaCalculos.setFont(new Font("Arial", Font.PLAIN, 12));
            areaCalculos.setText("");
            areaCalculos.setBackground(objetos);

            scroll.setBounds(350, 70, 325, 330);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            calcularAreaCalculos.setBounds(350, 410, 80, 30);
            calcularAreaCalculos.addActionListener(this);

            salvar.setBounds(435, 410, 70, 30);
            salvar.addActionListener(this);

            imprimir.setBounds(510,410,80,30);
            imprimir.addActionListener(this);

            limparAreaCalculos.setBounds(595, 410, 80, 30);
            limparAreaCalculos.addMouseListener(getMouseEvent());
            limparAreaCalculos.addActionListener(this);

            painel.add(nomeIndividuo);
            painel.add(dataInicial);
            painel.add(dataFinal);
            painel.add(calcular);
            painel.add(desfazer);
            painel.add(limpar);
            painel.add(novo);
            painel.add(scroll1);
            painel.add(scroll);
            painel.add(calcularAreaCalculos);
            painel.add(salvar);
            painel.add(imprimir);
            painel.add(limparAreaCalculos);

            setTitle("Tempo de Contribuição");
            setSize(700, 490);
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

            try {
                boolean datasSaoInvalidas = false;
                String textoDataInicial = dataInicial.getText();
                String textoDataFinal = dataFinal.getText();

                int diaDataInicial = Integer.parseInt(textoDataInicial.substring(0, 2));
                int mesDataInicial = Integer.parseInt(textoDataInicial.substring(3, 5));
                int anoDataInicial = Integer.parseInt(textoDataInicial.substring(6, 10));
                int diaDataFinal = Integer.parseInt(textoDataFinal.substring(0, 2));
                int mesDataFinal = Integer.parseInt(textoDataFinal.substring(3, 5));
                int anoDataFinal = Integer.parseInt(textoDataFinal.substring(6, 10));

                if (0 >= diaDataInicial || (diaDataInicial > 31 && (mesDataInicial == 1 || mesDataInicial == 3 || mesDataInicial == 5 || mesDataInicial == 7 || mesDataInicial == 8 || mesDataInicial == 10 || mesDataInicial == 12)) || (diaDataInicial > 30 && (mesDataInicial == 2 || mesDataInicial == 4 || mesDataInicial == 6 || mesDataInicial == 9 || mesDataInicial == 11)) || (diaDataInicial > 28 && mesDataInicial == 2 && anoDataInicial % 4 != 0) || (diaDataInicial > 29 && mesDataInicial == 2 && anoDataInicial % 4 == 0)) {
                    areaCalculos.append("A data inicial é inválida! \n\n");
                    datasSaoInvalidas = true;
                }
                if (0 >= diaDataFinal || (diaDataFinal > 31 && (mesDataFinal == 1 || mesDataFinal == 3 || mesDataFinal == 5 || mesDataFinal == 7 || mesDataFinal == 8 || mesDataFinal == 10 || mesDataFinal == 12)) || (diaDataFinal > 30 && (mesDataFinal == 2 || mesDataFinal == 4 || mesDataFinal == 6 || mesDataFinal == 9 || mesDataFinal == 11)) || (diaDataFinal > 28 && mesDataFinal == 2 && anoDataFinal % 4 != 0) || (diaDataFinal > 29 && mesDataFinal == 2 && anoDataFinal % 4 == 0)) {
                    areaCalculos.append("A data final é inválida! \n\n");
                    datasSaoInvalidas = true;
                }
                if (!datasSaoInvalidas) {
                    CalculaTempo(areaCalculos, dataInicial, dataFinal);
                }
            }
            catch (Exception ex){
                areaCalculos.append("Ocorreu o erro: " + (ex) + " Por favor, verifique se as datas estão corretas. \n\n");
            }
        }

        if(e.getSource() == desfazer){
            desfazerCalculos( listDoTempoDeTrabalho, listDeStringsDasSomasDiasEntreDuasDatas);
            Textos.desfazerTextos(areaCalculos, listDosTextosQueSeraoAdicionadosNoTextArea);
        }

        if(e.getSource() == limpar){
            Textos.limpar(nomeIndividuo, area, dataInicial, dataFinal);
        }

        if(e.getSource() == novo){
            new Calculadora();
        }

        if(e.getSource() == calcularAreaCalculos){
            Textos.MostraCalculoFinal(areaCalculos, tempoDeTrabalho, somaDiasEntreDuasDatas);
        }

        if(e.getSource() == salvar) Textos.salvarCalculos(nomeIndividuo, areaCalculos);

        if(e.getSource() == imprimir){
            Textos.imprimirCalculos(nomeIndividuo, areaCalculos);
        }

        if(e.getSource() == limparAreaCalculos){
            Textos.limparAreaCalculos(areaCalculos, listDosTextosQueSeraoAdicionadosNoTextArea, listDoTempoDeTrabalho, listDeStringsDasSomasDiasEntreDuasDatas);
            limparCalculos();
        }
    }

    private MouseAdapter getMouseEvent() {

        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

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

        localDataInicial = LocalDate.parse(dataInicial.getText(), formatoDosLocalDates);
        localDataFinal = LocalDate.parse(dataFinal.getText(), formatoDosLocalDates);
        localDataFinalSomadoComTempoDeTrabalho = localDataFinal.plus(tempoDeTrabalho);

        periodoDeTrabalho = Period.between(localDataInicial, localDataFinal);

        diasEntreDuasDatas = ChronoUnit.DAYS.between(localDataInicial, localDataFinal);

        tempoDeTrabalho = Period.between(localDataInicial, localDataFinalSomadoComTempoDeTrabalho);
        tempoDeTrabalho = tempoDeTrabalho.normalized();
        listDoTempoDeTrabalho.add(tempoDeTrabalho);

        somaDiasEntreDuasDatas = somaDiasEntreDuasDatas + diasEntreDuasDatas;
        listDeStringsDasSomasDiasEntreDuasDatas.add(String.valueOf(somaDiasEntreDuasDatas));

        ++ordemDosPeriodos;

        String textoQueSeraAdicionadoNoTextArea = ordemDosPeriodos + "° Periodo" +
                "\n" + "De " + localDataInicial.getDayOfMonth() + "/" + localDataInicial.getMonthValue()+ "/" + localDataInicial.getYear() + " até " +
                localDataFinal.getDayOfMonth() + "/" + localDataFinal.getMonthValue() + "/" + localDataFinal.getYear() +
                "\n" + "Duração: " +periodoDeTrabalho.getYears() + " Anos " +  periodoDeTrabalho.getMonths() + " Meses " + periodoDeTrabalho.getDays() + " e Dias" +
                "\n" + "Duração em Dias: " + diasEntreDuasDatas + "\n \n";

        listDosTextosQueSeraoAdicionadosNoTextArea.add(textoQueSeraAdicionadoNoTextArea);

        areaCalculos.append(textoQueSeraAdicionadoNoTextArea);
        Textos.limpar(this.dataInicial, this.dataFinal);

    }

    public void desfazerCalculos(ArrayList <Period> listDoTempoDeTrabalho, ArrayList <String> listDeStringsDasSomasDiasEntreDuasDatas){

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

    public void limparCalculos(){
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