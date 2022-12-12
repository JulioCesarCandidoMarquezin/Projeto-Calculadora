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

public class Calculadora extends JFrame implements ActionListener {

     Color objetos = new Color(198, 235, 255,255);
     JPanel painel = new JPanel();
     JFormattedTextField dataInicial, dataFinal;
     JTextField nomeIndividuo = new JTextField("");
     JButton calcular = new JButton("Calcular");
     JButton limpar = new JButton("Limpar");
     JButton novo = new JButton("Novo");
     JTextArea area = new JTextArea("");
     JTextArea areaCalculos = new JTextArea("");
     JButton calcularAreaCalculos = new JButton("Calcular");
     JButton salvar = new JButton("Salvar");
     JButton imprimir = new JButton("Imprimir");
     JButton limparAreaCalculos = new JButton("Limpar");
     JScrollPane scroll = new JScrollPane(areaCalculos);
     JScrollPane scroll1 = new JScrollPane(area);
    Period tempoDeTrabalho = Period.parse("P0Y0M0D");
    long somaDiasEntreDuasDatas = 0;
    int ordemDosPeriodos = 0;
    DateTimeFormatter formatoLocalDate;
    LocalDate localDataInicial, localDataFinal;
    Period periodoDeTrabalho;
    long diasEntreDuasDatas = 0;

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

            calcular.setBounds(10, 200, 105, 30);
            calcular.addActionListener(this);

            limpar.setBounds(120, 200, 105, 30);
            limpar.addActionListener(this);
            limpar.addMouseListener(getMouseEvent());
            limpar.getCursor();
            limpar.setToolTipText("Limpa as caixas de data, caixa de texto e area de anotações.");

            novo.setBounds(230, 200, 105, 30);
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
            painel.add(limpar);
            painel.add(scroll1);
            painel.add(novo);
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

        if(e.getSource() == calcular){

            boolean datasSaoInvalidas = false;
            String textoDataInicial = dataInicial.getText();
            String textoDataFinal = dataFinal.getText();
            int diaDataInicial = Integer.parseInt(textoDataInicial.substring(0,2));
            int mesDataInicial = Integer.parseInt(textoDataInicial.substring(3,5));
            int anoDataInicial = Integer.parseInt(textoDataInicial.substring(6,10));
            int diaDataFinal = Integer.parseInt(textoDataFinal.substring(0,2));
            int mesDataFinal = Integer.parseInt(textoDataFinal.substring(3,5));
            int anoDataFinal = Integer.parseInt(textoDataFinal.substring(6,10));

            if(0 >= diaDataInicial || (diaDataInicial > 31 && (mesDataInicial == 1 ||  mesDataInicial == 3 ||  mesDataInicial == 5 ||  mesDataInicial == 7 ||  mesDataInicial == 8 ||  mesDataInicial == 10 ||  mesDataInicial == 12)) || (diaDataInicial > 30 && (mesDataInicial == 2 ||  mesDataInicial == 4 ||  mesDataInicial == 6 ||  mesDataInicial == 9 ||  mesDataInicial == 11)) || (diaDataInicial > 28 && mesDataInicial == 2 && anoDataInicial%4 != 0) || (diaDataInicial > 29 && mesDataInicial == 2 && anoDataInicial%4 == 0)){
                areaCalculos.append("A data inicial é inválida! \n\n");
                datasSaoInvalidas = true;
            }
            if(0 >= diaDataFinal || (diaDataFinal > 31 && (mesDataFinal == 1 ||  mesDataFinal == 3 ||  mesDataFinal == 5 ||  mesDataFinal == 7 ||  mesDataFinal == 8 ||  mesDataFinal == 10 ||  mesDataFinal == 12)) || (diaDataFinal > 30 && (mesDataFinal == 2 ||  mesDataFinal == 4 ||  mesDataFinal == 6 ||  mesDataFinal == 9 ||  mesDataFinal == 11)) || (diaDataFinal > 28 && mesDataFinal == 2 && anoDataFinal%4 != 0) || (diaDataFinal > 29 && mesDataFinal == 2 && anoDataFinal%4 == 0)){
                areaCalculos.append("A data final é inválida! \n\n");
                datasSaoInvalidas = true;
            }
            if(!datasSaoInvalidas){
                CalculaTempo( areaCalculos, dataInicial, dataFinal);
            }
        }

        if(e.getSource() == limpar){
            Textos.limpar(nomeIndividuo, area, dataInicial, dataFinal);
        }

        if(e.getSource() == novo){
            new Calculadora();
        }

        if(e.getSource() == calcularAreaCalculos){
            MostraCalculoFinal(areaCalculos);
        }

        if(e.getSource() == salvar) {
            Textos.salvarCalculos(nomeIndividuo, areaCalculos);
        }

        if(e.getSource() == imprimir){
            Textos.imprimirCalculos(nomeIndividuo, areaCalculos);
        }

        if(e.getSource() == limparAreaCalculos){
            Textos.limparAreaCalculos(areaCalculos);
        }
    }

    private MouseAdapter getMouseEvent() {

        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                if(e.getSource() == limpar){
                    limpar.setText("Certeza?");
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

                if(e.getSource() == limparAreaCalculos){
                    limparAreaCalculos.setText("Limpar");
                }

            }

        };
    }

    protected void CalculaTempo(JTextArea areaCalculos, JTextField dataInicial, JTextField dataFinal){

        formatoLocalDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        localDataInicial = LocalDate.parse(dataInicial.getText(), formatoLocalDate);
        localDataFinal = LocalDate.parse(dataFinal.getText(), formatoLocalDate);

        periodoDeTrabalho = Period.between(localDataInicial, localDataFinal);
        diasEntreDuasDatas = ChronoUnit.DAYS.between(localDataInicial, localDataFinal);

        // Problema: "tempoDeTrabalho" não passa o excedente de dias para meses //

        tempoDeTrabalho = tempoDeTrabalho.plus(periodoDeTrabalho);
        somaDiasEntreDuasDatas = somaDiasEntreDuasDatas + diasEntreDuasDatas;
        tempoDeTrabalho = tempoDeTrabalho.normalized();
        ++ordemDosPeriodos;

        areaCalculos.append(ordemDosPeriodos + "° Periodo" +
                "\n" + "De " + localDataInicial.getDayOfMonth() + "/" + localDataInicial.getMonthValue()+ "/" + localDataInicial.getYear() + " até " +
                localDataFinal.getDayOfMonth() + "/" + localDataFinal.getMonthValue() + "/" + localDataFinal.getYear() +
                "\n" + "Duração: " +periodoDeTrabalho.getYears() + " Anos " +  periodoDeTrabalho.getMonths() + " Meses " + periodoDeTrabalho.getDays() + " e Dias" +
                "\n" + "Duração em Dias: " + diasEntreDuasDatas + "\n \n");
        Textos.limpar(this.dataInicial, this.dataFinal);
    }

    protected void MostraCalculoFinal(JTextArea AreaCalculos){
        AreaCalculos.append("Tempo total de trabalho: " + tempoDeTrabalho.getYears() + " Anos " + tempoDeTrabalho.getMonths() + " Meses e " + tempoDeTrabalho.getDays() + " Dias"
                + "\n" + "Tempo total em Dias: " + somaDiasEntreDuasDatas + "\n\n");
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