import javax.print.*;
import javax.print.attribute.*;
import javax.swing.*;
import java.io.*;
import java.time.Period;
import java.util.ArrayList;

public class Textos   {

    protected static void limpar(JTextField nomeIndividuo, JTextArea area, JFormattedTextField dataInicial, JFormattedTextField dataFinal){

        nomeIndividuo.setText("");
        dataInicial.setText("");
        dataInicial.setValue("");
        dataFinal.setText("");
        dataFinal.setValue("");
        area.setText("");
    }

    protected static void limpar(JFormattedTextField dataInicial, JFormattedTextField dataFinal){

        dataInicial.setText("");
        dataInicial.setValue("");
        dataFinal.setText("");
        dataFinal.setValue("");
    }

    protected static void MostraCalculoFinal(JTextArea areaCalculos, Period tempoDeTrabalho, long somaDiasEntreDuasDatas, ArrayList <String> listDosTextosDoMostrarCalculoFinal){
        if(!tempoDeTrabalho.isZero() && somaDiasEntreDuasDatas != 0){
            String textoQueSeraAdicionadoNoTextArea = "Tempo total de trabalho: " + tempoDeTrabalho.getYears() + " Anos " + tempoDeTrabalho.getMonths() + " Meses e " + tempoDeTrabalho.getDays() + " Dias"
                    + "\n" + "Tempo total em Dias: " + somaDiasEntreDuasDatas + "\n\n";
            areaCalculos.append(textoQueSeraAdicionadoNoTextArea);
            listDosTextosDoMostrarCalculoFinal.add(textoQueSeraAdicionadoNoTextArea);
        }
    }

    protected static void removerTextosInuteis(JTextArea areaCalculos, ArrayList <String> listDosTextosDoMostrarCalculoFinal){
        while(listDosTextosDoMostrarCalculoFinal.size() > 0){
            String textoQueSeraTiradoDoTextArea = listDosTextosDoMostrarCalculoFinal.remove(listDosTextosDoMostrarCalculoFinal.size() - 1);
            String novoTextoDoTextArea = areaCalculos.getText().replace(textoQueSeraTiradoDoTextArea, "");
            areaCalculos.setText(novoTextoDoTextArea);
        }
        String textoDeDataInvalida;
        String novoTextoDoTextArea;
        textoDeDataInvalida = "A data inicial é inválida! \n\n";
        novoTextoDoTextArea = areaCalculos.getText().replace(textoDeDataInvalida, "");
        areaCalculos.setText(novoTextoDoTextArea);

        textoDeDataInvalida = "A data final é inválida! \n\n";
        novoTextoDoTextArea = areaCalculos.getText().replace(textoDeDataInvalida, "");
        areaCalculos.setText(novoTextoDoTextArea);

        textoDeDataInvalida = "A data inicial é maior que a data final \n\n";
        novoTextoDoTextArea = areaCalculos.getText().replace(textoDeDataInvalida, "");
        areaCalculos.setText(novoTextoDoTextArea);
    }

    protected static void salvarCalculos(JTextField nomeIndividuo, JTextArea areaCalculos, Period tempoDeTrabalho, long somaDiasEntreDuasDatas, ArrayList <String> listDosTextosDoMostrarCalculoFinal){

        try {
            removerTextosInuteis(areaCalculos, listDosTextosDoMostrarCalculoFinal);
            MostraCalculoFinal(areaCalculos, tempoDeTrabalho, somaDiasEntreDuasDatas, listDosTextosDoMostrarCalculoFinal);

            File arquivoQueSeraSalvo;
            if(nomeIndividuo.getText().equals("")){
                arquivoQueSeraSalvo = new File("Arquivo.txt");
            }
            else{
                arquivoQueSeraSalvo = new File(nomeIndividuo.getText() + ".txt");
            }

            JFileChooser escolhedorDoArquivoQueSeraSalvo = new JFileChooser();
            escolhedorDoArquivoQueSeraSalvo.setSelectedFile(arquivoQueSeraSalvo);

            int opcaoEscolhidaParaSerSalva = escolhedorDoArquivoQueSeraSalvo.showDialog(null, "Salvar");
            if(opcaoEscolhidaParaSerSalva == 0){
                arquivoQueSeraSalvo = new File("" +  escolhedorDoArquivoQueSeraSalvo.getSelectedFile() );
                RandomAccessFile raf = new RandomAccessFile(arquivoQueSeraSalvo, "rw");
                raf.writeBytes(areaCalculos.getText());
                raf.close();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Ver questão do arquivo em branco que a impressora faz && ver ProcessStartInfo parece interessante para pegar funções do Windows //
    protected static void imprimirCalculos(JTextField nomeIndividuo, JTextArea areaCalculos, Period tempoDeTrabalho, long somaDiasEntreDuasDatas, ArrayList <String> listDosTextosDoMostrarCalculoFinal){
        try {

            removerTextosInuteis(areaCalculos, listDosTextosDoMostrarCalculoFinal);
            MostraCalculoFinal(areaCalculos, tempoDeTrabalho, somaDiasEntreDuasDatas, listDosTextosDoMostrarCalculoFinal);

            // O documento temporário é criado//
            File arquivoTemporarioQueSeraImpresso;
            if(nomeIndividuo.getText().equals("")){
                arquivoTemporarioQueSeraImpresso = new File("Arquivo.txt");
            }
            else{
                arquivoTemporarioQueSeraImpresso = new File(nomeIndividuo.getText() + ".txt");
            }

            // O texto que está no JTextArea será escrito no documento//
            RandomAccessFile alteradorDoTextoDoArquivoTemporario = new RandomAccessFile(arquivoTemporarioQueSeraImpresso, "rw");
            alteradorDoTextoDoArquivoTemporario.writeBytes(areaCalculos.getText());
            alteradorDoTextoDoArquivoTemporario.close();

            // A partir daqui será feito o que é preciso para a impressão//
            PrintService [] impressorasDisponiveis = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.AUTOSENSE, null);

            PrintService impressoraPadrao = PrintServiceLookup.lookupDefaultPrintService();

            DocFlavor atributosDeImpressao = DocFlavor.INPUT_STREAM.AUTOSENSE;

            // fornece Um Conjunto De Atributos Que Herda De Sua Implementação //
            HashDocAttributeSet hashDocAttributeSet = new HashDocAttributeSet();

            FileInputStream arquivoQueSeraImpresso = new FileInputStream(arquivoTemporarioQueSeraImpresso);

            Doc documento = new SimpleDoc(arquivoQueSeraImpresso, atributosDeImpressao, hashDocAttributeSet);

            // configuracoes Que Serao Aplicadas Na Impressao //
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();

            //descreve Os Resursos De Impressao Podendo Ser Consultado Sobre Os Atributos Suportados Por Uma Impressora//
            PrintService printService = ServiceUI.printDialog(null, 0,0,impressorasDisponiveis,impressoraPadrao,atributosDeImpressao,printRequestAttributeSet);

            if(printService != null && impressorasDisponiveis.length > 0){

                // interface Que Representa Um Trabalho De Impressao Que Pode Imprimir Um Documento Especificado Com Um Conjunto De Atributos De Trabalho //
                DocPrintJob docPrintJob = printService.createPrintJob();

                //Manda imprimir o documento//
                docPrintJob.print(documento,printRequestAttributeSet);
            }
            // Fecha este fluxo de entrada de arquivo e libera todos os recursos do sistema associados ao fluxo //
            arquivoQueSeraImpresso.close();

            //Quando o programa fecha o arquivo temporário é deletado//
            arquivoTemporarioQueSeraImpresso.deleteOnExit();
        }
        catch (IOException | PrintException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void limparAreaCalculos(JTextArea areaCalculos, ArrayList <String> listDosTextosQueSeraoAdicionadosNoTextArea, ArrayList <Period> listDoTempoDeTrabalho , ArrayList <String> listDeStringsDasSomasDiasEntreDuasDatas){

        listDosTextosQueSeraoAdicionadosNoTextArea.clear();
        listDoTempoDeTrabalho.clear();
        listDeStringsDasSomasDiasEntreDuasDatas.clear();
        areaCalculos.setText("");

    }

    protected static void desfazerTextos(JTextArea areaCalculos, ArrayList <String> listDosTextosQueSeraoAdicionadosNoTextArea){

        int ultimaStringDaList = listDosTextosQueSeraoAdicionadosNoTextArea.size() -1;
        if (listDosTextosQueSeraoAdicionadosNoTextArea.size() > 0){
            String textoQueSeraTiradoDoTextArea = listDosTextosQueSeraoAdicionadosNoTextArea.remove(ultimaStringDaList);
            String novoTextoDoTextArea = areaCalculos.getText().replace(textoQueSeraTiradoDoTextArea, "");
            areaCalculos.setText(novoTextoDoTextArea);
        }
    }
}