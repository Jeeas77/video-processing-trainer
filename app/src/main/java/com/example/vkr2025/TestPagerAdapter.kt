package com.example.vkr2025

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TestPagerAdapter(
    fa: FragmentActivity,
    private val testTitle: String // Принимаем название теста в конструкторе
) : FragmentStateAdapter(fa) {


    private val questions: List<TestQuestion> = when(testTitle) {
        "Технологии и обработка аудио и видеоинформации" -> listOf(
            TestQuestion("Самый распространенный формат аудиофайла?", listOf("MP3","AVI","WAV"), 0),
            TestQuestion("Какой формат относится к цифровой видеоинформации и сопровождающей ее аудиоинформации?", listOf("WMA","MIDI","MOV"), 2),
            TestQuestion("Выберите из предложенных вариантов название видеокодека?", listOf("H.261","Divxc","Mpeg88"), 0),
            TestQuestion("С помощью какого редактора редактируют аудиофайлы?", listOf("Convertilla","Audacity","VideoPad"), 1),
            TestQuestion("С помощью какого редактора редактируют видеофайлы?", listOf("Convertilla","MIDI","VideoPad"), 2),
            TestQuestion("С помощью какого редактора конвертируют видеоформаты?", listOf("Convertilla","Audacity","VideoPad"), 0),
            TestQuestion("Каким сочетанием клавиш переместиться в конец видеоряда?", listOf("Ctrl + Shift + End","Ctrl + Shift + Home","Ctrl + Home + Shift"), 0),
            TestQuestion("Выберите из ниже перечисленного качество записи речи в диктофонах?", listOf("192 кбит/с","32 кбит/с","64 кбит/с"), 1),
            TestQuestion("Наивысший уровень качества звукозаписи, поддерживаемый форматом MP3?", listOf("300 кбит/с","420 кбит/с","320 кбит/с"), 2),
            TestQuestion("Сохранение видеофильма как проекта в файле с расширением?", listOf(".vpj",".aux",".video"), 0),
        )
        "Технология мультимедиа" -> listOf(
            TestQuestion("Какая часть компьютерной игры является мультимедийным продуктом?", listOf("вся игра полностью является мультимедийным продуктом","анимационная составляющая","ролики-заставки, вставленные в игру"), 0),
            TestQuestion("Как ещё можно назвать интерактивный режим работы?", listOf("динамический","диалоговый ","сетевой"), 1),
            TestQuestion("Какой один из основных недостатков мультимедийных продуктов?", listOf("требовательны к операционной системе","требуют использования дорогостоящей аппаратуры","требуют большого объёма памяти"), 2),
            TestQuestion("Какой элемент компьютера преображает звук из непрерывной формы в дискретную и наоборот?", listOf("звуковая карта","аудио кодеки","микрофон"), 0),
            TestQuestion("Что такое амплитуда звука?", listOf("высота звука","количество колебаний в секунду","сила звука"), 2),
            TestQuestion("Что из перечисленного является примером использования мультимедийных технологий в культуре?", listOf("покупка билета в музей через интернет","виртуальные экскурсии по музеям","цифровые репродукции картин"), 1),
            TestQuestion("Как дословно переводится с латинского языка термин «мультимедиа»?", listOf("«Большой объём»","«Многие знания»","«Многие средства»"), 2),
            TestQuestion("Что предпринимается, чтобы объём видеофайла не был чрезмерно большим?", listOf("используются специальные алгоритмы сжатия","большой видеофайл разделяют на несколько частей","содержимое видеофайла сокращают, оставляя только самое существенное"), 0),
            TestQuestion("Какое из этих устройств не требуется для работы с мультимедийными продуктами?", listOf("звуковая карта","микрофон","принтер"), 2),
            TestQuestion("Что такое аудиоадаптер?", listOf("переходник для разъёма колонок или микрофона","другое название звуковой карты","программа, преобразующая компьютерный код в звук и обратно"), 1),
        )
        "Технологии обработки видеоинформации" -> listOf(
            TestQuestion("Какой формат сжатия видео использует алгоритмы межкадровой компрессии?", listOf("AVI","H.264/AVC","Motion JPEG"), 1),
            TestQuestion("Что такое битрейт в видео?", listOf("Количество кадров в секунду","Объем данных, передаваемых за единицу времени","Разрешение видеоизображения"), 1),
            TestQuestion("Какой цветовой модели обычно придерживаются в цифровом видео?", listOf("RGB","CMYK","YUV"), 2),
            TestQuestion("Что означает аббревиатура 'FPS' в видеообработке?", listOf("Full Pixel Scaling","Frames Per Second","Final Production Stage"), 1),
            TestQuestion("Какой процесс позволяет уменьшить 'рваность' движения в видео?", listOf("Декомпрессия","Интерполяция кадров","Нормализация аудио"), 1),
            TestQuestion("Что такое 'хромакей' в видеообработке?", listOf("Тип цветовой коррекции","Технология замены фона","Формат сжатия видео"), 1),
            TestQuestion("Какой параметр больше всего влияет на 'вес' видеофайла?", listOf("«Частота кадров»","«Битрейт»","«Оба параметра одинаково»"), 2),
            TestQuestion("Что такое 'кодек' в контексте видео?", listOf("Устройство для записи видео","Алгоритм сжатия/распаковки данных","Формат файла"), 1),
        )
        else -> emptyList()

    }


    override fun getItemCount(): Int = questions.size



    override fun createFragment(position: Int): Fragment {
        return TestQuestionFragment.newInstance(
            questions[position].questionText,
            questions[position].options,
            questions[position].correctAnswerIndex
        )

    }



    data class TestQuestion(
        val questionText: String,
        val options: List<String>,
        val correctAnswerIndex: Int,
        val questionType: QuestionType = QuestionType.SINGLE_CHOICE
    )

    enum class QuestionType {
        SINGLE_CHOICE, MULTIPLE_CHOICE, TEXT_ANSWER
    }


    fun getTestTitle(): String {
        return testTitle
    }
}


