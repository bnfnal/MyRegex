package ru.bnfnal

import java.awt.Color
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.regex.Pattern
import javax.swing.*
import javax.swing.text.DefaultHighlighter


class Regex: JFrame() {
    val minSz = Dimension(600, 600)
    private val btnChooze: JButton
    private val btnFind: JButton
    private var lbl: JLabel
    private var tf: JTextField
    private var ta: JTextArea

    private var textFromFile = """"""
    private var words: MutableList<String> = mutableListOf()
    private var pattern: String = ""
    val painter = DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY)

    init{
        minimumSize = minSz
        defaultCloseOperation = EXIT_ON_CLOSE
        setLocationRelativeTo(null);

        btnChooze = JButton()
        btnChooze.text = "Выбрать файл"

        btnFind = JButton()
        btnFind.text = "Найти выбранные слова и их словоформы"

        lbl = JLabel().apply {
            text = "Введите слова, которые хотите найти, через пробел"
        }

        tf = JTextField()
        ta = JTextArea()

        btnChooze.addMouseListener(object : MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?) {
                if (e?.button == 1) {
                    val jfc = JFileChooser()
                    jfc.showDialog(null, "Выберите текстовый файл")
                    jfc.isVisible = true
                    textFromFile = jfc.selectedFile.readText()
                    ta.text = textFromFile.trimIndent()
                }
            }
        })

        btnFind.addMouseListener(object : MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?) {
                if (e?.button == 1) {
                    if (textFromFile != null && textFromFile != "") {
                        ta.getHighlighter().removeAllHighlights()
                        ta.grabFocus()
                        if (tf.text != null && tf.text != "" ){
                            val s = tf.text.toString()
                            words = s.split(" ").toMutableList()
                            pattern = """"""
                            for (i in 0 until words.size) {
                                if (i > 0) pattern += """|"""
                                pattern +=
                                    """(?<=\b)(?:(?:[A-Za-zА-Яа-я0-9]+)?""" + words[i] + """(?:[A-Za-zА-Яа-я0-9]+)?)(?=\b)"""
                            }
                            val p = Pattern.compile(pattern, Pattern.MULTILINE or Pattern.UNICODE_CHARACTER_CLASS)
                            val m = p.matcher(ta.text)
                            while (m.find()){
                                ta.getHighlighter().addHighlight(m.start(), m.end(), painter);
                            }
                            ta.grabFocus()
                        }
                    }

                }
            }
        })

        addComponentListener(object : ComponentAdapter(){
            override fun componentResized(e: ComponentEvent?) {
                repaint()
            }
        })

        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(lbl, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            .addComponent(btnFind, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(ta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            .addComponent(btnChooze, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    )
                    .addGap(8)
            )


            setVerticalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addComponent(lbl, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(4)
                    .addComponent(tf, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(4)
                    .addComponent(btnFind, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(8)
                    .addComponent(ta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                    .addGap(4)
                    .addComponent(btnChooze, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(8)
            )
        }
    }
}