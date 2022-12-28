import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.*;
import java.time.Period;
import java.util.ArrayList;

public class Textos   {

    protected static void limparTudo(JTextField nomeIndividuo, JTextArea area, JFormattedTextField dataInicial, JFormattedTextField dataFinal, JTextArea areaCalculos, ArrayList <String> listDosTextosQueSeraoAdicionadosNoTextAreaPeloCalcular, ArrayList <Period> listDoTempoDeTrabalho, ArrayList <String> listDeStringsDasSomasDiasEntreDuasDatas){

        limparDatas(dataInicial, dataFinal);
        nomeIndividuo.setText("");
        area.setText("");
        listDosTextosQueSeraoAdicionadosNoTextAreaPeloCalcular.clear();
        listDoTempoDeTrabalho.clear();
        listDeStringsDasSomasDiasEntreDuasDatas.clear();
        areaCalculos.setText("");
    }

    protected static void limparDatas(JFormattedTextField dataInicial, JFormattedTextField dataFinal){

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
            String textoQueSeraTiradoDoTextArea = listDosTextosDoMostrarCalculoFinal.get(listDosTextosDoMostrarCalculoFinal.size() - 1);
            String novoTextoDoTextArea = areaCalculos.getText().replace(textoQueSeraTiradoDoTextArea, "");
            areaCalculos.setText(novoTextoDoTextArea);
            listDosTextosDoMostrarCalculoFinal.remove(textoQueSeraTiradoDoTextArea);
        }

        String textoDeDataInvalida;
        String novoTextoDoTextArea;
        textoDeDataInvalida = "Data Inválida\n\n";
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

    protected static void desfazerTextos(JTextArea areaCalculos, ArrayList <String> listDosTextosQueSeraoAdicionadosNoTextAreaPeloCalcular){

        if (listDosTextosQueSeraoAdicionadosNoTextAreaPeloCalcular.size() > 0){
            String textoQueSeraTiradoDoTextArea = listDosTextosQueSeraoAdicionadosNoTextAreaPeloCalcular.remove(listDosTextosQueSeraoAdicionadosNoTextAreaPeloCalcular.size() - 1);
            String novoTextoDoTextArea = areaCalculos.getText().replace(textoQueSeraTiradoDoTextArea, "");
            areaCalculos.setText(novoTextoDoTextArea);
        }
    }
}