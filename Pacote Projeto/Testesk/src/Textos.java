import javax.print.*;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Textos {

    public static void limpar(JTextField nomeIndividuo, JTextArea area, JFormattedTextField dataInicial, JFormattedTextField dataFinal){

        nomeIndividuo.setText("");
        dataInicial.setText("");
        dataInicial.setValue("");
        dataFinal.setText("");
        dataFinal.setValue("");
        area.setText("");
    }

    public static void limpar(JFormattedTextField dataInicial, JFormattedTextField dataFinal){

        dataInicial.setText("");
        dataInicial.setValue("");
        dataFinal.setText("");
        dataFinal.setValue("");
    }

    public static void limparAreaCalculos(JTextArea areaCalculos){
        areaCalculos.setText("");
    }

    //Arrumar o problema de não poder abrir o pdf //
    public static void salvarCalculos(JTextField nomeIndividuo, JTextArea areaCalculos){

        try {
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

    // Ver questão do arquivo em branco que a impressora faz //
    public static void imprimirCalculos(JTextField nomeIndividuo, JTextArea areaCalculos){
        try {

            // O documento temporário é criado//
            File arquivoTemporarioQueSeraImpresso = new File(nomeIndividuo.getText() + ".txt");

            // O texto que está no JTextArea será escrito no documento//
            RandomAccessFile alteradorDoTextoDoArquivoTemporario = new RandomAccessFile(arquivoTemporarioQueSeraImpresso, "rw");
            alteradorDoTextoDoArquivoTemporario.writeBytes(areaCalculos.getText());
            alteradorDoTextoDoArquivoTemporario.close();

            // A partir daqui será feito o que é preciso para a impressão//
            PrintService [] impressorasDisponiveis = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.AUTOSENSE, null);

            PrintService impressoraPadrao = PrintServiceLookup.lookupDefaultPrintService();

            DocFlavor atributosDeImpressao = DocFlavor.INPUT_STREAM.AUTOSENSE;

            HashDocAttributeSet forneceUmConjuntoDeAtributosQueHerdaSuaImplementação = new HashDocAttributeSet();

            FileInputStream arquivoQueSeraImpresso = new FileInputStream(arquivoTemporarioQueSeraImpresso);

            Doc documento = new SimpleDoc(arquivoQueSeraImpresso, atributosDeImpressao, forneceUmConjuntoDeAtributosQueHerdaSuaImplementação);

            PrintRequestAttributeSet configuracoesQueSeraoAplicadasNaImpressao = new HashPrintRequestAttributeSet();

            //descreve Os Resursos De Impressao Podendo Ser Consultado Sobre Os Atributos Suportados Por Uma Impressora//
            PrintService printService = ServiceUI.printDialog(null, 300,200,impressorasDisponiveis,impressoraPadrao,atributosDeImpressao,configuracoesQueSeraoAplicadasNaImpressao);

            if(printService != null){

                // interface Que Representa Um Trabalho De Impressao Que Pode Imprimir Um Documento Especificado Com Um Conjunto De Atributos De Trabalho //
                DocPrintJob docPrintJob = printService.createPrintJob();

                //Manda imprimir o documento//
                docPrintJob.print(documento,configuracoesQueSeraoAplicadasNaImpressao);
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
}