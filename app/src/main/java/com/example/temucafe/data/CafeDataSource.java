package com.example.temucafe.data;

import com.example.temucafe.R;
import com.example.temucafe.models.Cafe;

import java.util.ArrayList;
import java.util.Arrays;

public class CafeDataSource {
    public static ArrayList<Cafe> getSampleCafes() {
        ArrayList<Cafe> cafes = new ArrayList<>();

        cafes.add(new Cafe(
                "cafe1",
                "Cecemuwe Cafe and Space",
                "Jl. Hang Jebat IX No.6, RT.8/RW.4, Gunung, Kec. Kby. Baru, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12120",
                "10:00 - 22:00",
                4.5f,
                R.drawable.cecemuwe_cafe_and_cpace,
                -6.235412955729069,
                106.79336881164402,
                new ArrayList<>(Arrays.asList(
                        R.drawable.gallery1_cecemuwe_cafe_and_space,
                        R.drawable.gallery2_cecemuwe_cafe_and_space,
                        R.drawable.gallery3_cecemuwe_cafe_and_space
                )),
                "Jakarta Selatan"
        ));

        cafes.add(new Cafe(
                "cafe2",
                "Gordi HQ",
                "Jl. Jeruk Purut Dalam No.25, RT.6/RW.3, Cilandak Tim., Ps. Minggu, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12560",
                "07:30 - 21:00",
                4.6f,
                R.drawable.gordi_hq,
                -6.284053035032206,
                106.8117017929605,
                new ArrayList<>(Arrays.asList(
                        R.drawable.gallery1_gordi_hq,
                        R.drawable.gallery2_gordi_hq,
                        R.drawable.gallery3_gordi_hq,
                        R.drawable.gallery4_gordi_hq,
                        R.drawable.gallery5_gordi_hq
                )),
                "Jakarta Selatan"
        ));

        cafes.add(new Cafe(
                "cafe3",
                "First Crack Coffee",
                "Jl. Bumi No.10 2, RT.2/RW.3, Gunung, Kec. Kby. Baru, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12120",
                "07:00 - 22:00",
                4.8f,
                R.drawable.first_crack_coffee,
                -6.2369662217859885,
                106.79002397946687,
                new ArrayList<>(Arrays.asList(
                        R.drawable.gallery1_first_crack_coffee,
                        R.drawable.gallery2_first_crack_coffee,
                        R.drawable.gallery3_first_crack_coffee,
                        R.drawable.gallery4_first_crack_coffee
                )),
                "Jakarta Selatan"
        ));

        cafes.add(new Cafe(
                "cafe4",
                "Pison Coffee",
                "Jl. Kertanegara Jl. Senopati No.70, Rw. Bar., Kec. Kby. Baru, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12160",
                "08:00 - 22:00",
                4.5f,
                R.drawable.pison_cafe,
                -6.233399324064959,
                106.81191976782269,
                new ArrayList<>(Arrays.asList(
                        R.drawable.gallery1_pison_coffee,
                        R.drawable.gallery2_pison_coffee,
                        R.drawable.gallery3_pison_coffee,
                        R.drawable.gallery4_pison_coffee
                )),
                "Jakarta Selatan"
        ));

        cafes.add(new Cafe(
                "cafe5",
                "Work Coffee Jakarta",
                "Jl. H. Ipin No.81A, Lb. Bulus, Kec. Cilandak, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12440",
                "08:00 - 22:00",
                4.6f,
                R.drawable.work_coffee_jakarta,
                -6.310722100145584,
                106.78663453528952,
                new ArrayList<>(Arrays.asList(
                        R.drawable.gallery1_work_coffee_jakarta,
                        R.drawable.gallery2_work_coffee_jakarta,
                        R.drawable.gallery3_work_coffee_jakarta
                )),
                "Jakarta Selatan"
        ));

        cafes.add(new Cafe(
                "cafe6",
                "Kaneel Cafe",
                "Jl. Tanjung Duren Raya No.12, RT.14/RW.7, Tanjung Duren Raya, Kec. Grogol petamburan, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11470",
                "10:00 - 22:00",
                4.5f,
                R.drawable.kaneel_coffee,
                -6.18306293175056,
                106.7833447524799,
                new ArrayList<>(Arrays.asList(
                        R.drawable.gallery1_kaneel_coffee,
                        R.drawable.gallery2_kaneel_coffee,
                        R.drawable.gallery3_kaneel_coffee
                )),
                "Jakarta Barat"
        ));

        cafes.add(new Cafe(
                "cafe7",
                "DUE Coffee and Kitchen",
                "Jl. Raya Klp. Dua No.2, RT.2/RW.8, Klp. Dua, Kec. Kb. Jeruk, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11550",
                "08:00 - 22:00",
                4.5f,
                R.drawable.due_coffee,
                -6.208418273278079,
                106.76954191200052,
                new ArrayList<>(Arrays.asList(
                        R.drawable.gallery1_due_coffee,
                        R.drawable.gallery2_due_coffee,
                        R.drawable.gallery3_due_coffee,
                        R.drawable.gallery4_due_coffee
                )),
                "Jakarta Barat"
        ));

        cafes.add(new Cafe(
                "cafe8",
                "Sudutsatu Cafe",
                "Jl. Tj. Duren Barat I No.1c, Tj. Duren Utara, Kec. Grogol petamburan, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11470",
                "10:00 - 21:30",
                4.5f,
                R.drawable.sudu_satu,
                -6.173820694473238,
                106.78014782364419,
                new ArrayList<>(Arrays.asList(
                        R.drawable.gallery1_sudut_satu,
                        R.drawable.gallery2_sudut_satu,
                        R.drawable.gallery3_sudut_satu
                )),
                "Jakarta Barat"
        ));

        cafes.add(new Cafe(
                "cafe9",
                "Guten Mørgen",
                "Jl. Mandala Utara No.29C 17, RT.17/RW.4, Tomang, Kec. Grogol petamburan, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11440",
                "08:00 - 21:00",
                4.6f,
                R.drawable.guten_morgen,
                -6.173714028354291,
                106.78019073898659,
                new ArrayList<>(Arrays.asList(
                        R.drawable.gallery1_guten_morgen,
                        R.drawable.gallery2_guten_morgen,
                        R.drawable.gallery3_guten_morgen,
                        R.drawable.gallery4_guten_morgen
                )),
                "Jakarta Barat"
        ));

        cafes.add(new Cafe(
                "cafe10",
                "+62 Coffee & Space",
                "Jl. Mandala Utara No.29C 17, RT.17/RW.4, Tomang, Kec. Grogol petamburan, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11440",
                "08:00 - 21:00",
                4.6f,
                R.drawable.enamdua_coffee,
                -6.17361080708272, 106.78573337180406,
                new ArrayList<>(Arrays.asList(
                        R.drawable.gallery1_62_coffee,
                        R.drawable.gallery2_62_coffee,
                        R.drawable.gallery3_62_coffee
                )),
                "Jakarta Barat"
        ));

        cafes.add(new Cafe(
                "cafe11",
                "Narasi Coffee & Eatery",
                "Jl. Danau Agung 2 No.28, RT.20/RW.10, Sunter Agung, Kec. Tj. Priok, Jkt Utara, Daerah Khusus Ibukota Jakarta 14350",
                "08:00 - 22:00",
                4.5f,
                R.drawable.placeholder,
                -5.235879125272809,
                106.79331736597358,
                new ArrayList<>(Arrays.asList(
                        R.drawable.placeholder,
                        R.drawable.placeholder,
                        R.drawable.placeholder
                )),
                "Jakarta Utara"
        ));

        cafes.add(new Cafe(
                "cafe12",
                "Amyréa Art & Kitchen",
                "Jalan Gading Putih Raya CA2, No.12, RT.11/RW.12, Klp. Gading Tim., Kec. Klp. Gading, Jkt Utara, Daerah Khusus Ibukota Jakarta 14240",
                "08:00 - 22:00",
                4.5f,
                R.drawable.placeholder,
                -5.235879125272809,
                106.79331736597358,
                new ArrayList<>(Arrays.asList(
                        R.drawable.placeholder,
                        R.drawable.placeholder,
                        R.drawable.placeholder
                )),
                "Jakarta Utara"
        ));

        cafes.add(new Cafe(
                "cafe13",
                "Copper Club Specialty Coffee",
                "Jl. Tarian Raya Tim. No.W1 no 23, RT.5/RW.9, Pegangsaan Dua, Kec. Klp. Gading, Jkt Utara, Daerah Khusus Ibukota Jakarta 14240",
                "07:00 - 21:00",
                4.5f,
                R.drawable.placeholder,
                -5.235879125272809,
                106.79331736597358,
                new ArrayList<>(Arrays.asList(
                        R.drawable.placeholder,
                        R.drawable.placeholder,
                        R.drawable.placeholder
                )),
                "Jakarta Utara"
        ));

        cafes.add(new Cafe(
                "cafe14",
                "Kohicha Japanese Cafe",
                "Ruko Gading Kirana Jalan Boulevard Artha Gading No.Blok A.6a No.21, RT.18/RW.8, Klp. Gading Bar., Kec. Klp. Gading, Jkt Utara, Daerah Khusus Ibukota Jakarta 14240",
                "08:00 - 22:00",
                4.5f,
                R.drawable.placeholder,
                -5.235879125272809,
                106.79331736597358,
                new ArrayList<>(Arrays.asList(
                        R.drawable.placeholder,
                        R.drawable.placeholder,
                        R.drawable.placeholder
                )),
                "Jakarta Utara"
        ));

        cafes.add(new Cafe(
                "cafe15",
                "Kopilot Coffee House And Kitchen",
                "Jl. Raya Pd. Gede No.1, RW.3, Halim Perdanakusuma, Kec. Cipayung, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13810",
                "10:00 - 00:00",
                4.5f,
                R.drawable.placeholder,
                -5.235879125272809,
                106.79331736597358,
                new ArrayList<>(Arrays.asList(
                        R.drawable.placeholder,
                        R.drawable.placeholder,
                        R.drawable.placeholder
                )),
                "Jakarta Timur"
        ));

        cafes.add(new Cafe(
                "cafe16",
                "Scent Coffee",
                "Jl. Kejaksaan Raya No.20, Pd. Bambu, Kec. Duren Sawit, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13430",
                "08:00 - 23:00",
                4.5f,
                R.drawable.placeholder,
                -5.235879125272809,
                106.79331736597358,
                new ArrayList<>(Arrays.asList(
                        R.drawable.placeholder,
                        R.drawable.placeholder,
                        R.drawable.placeholder
                )),
                "Jakarta Timur"
        ));

        cafes.add(new Cafe(
                "cafe17",
                "KOCIL Specialty Coffee Jackfruit Island",
                "Kav.1, Jl. Kayu Putih Raya No.1, RT.8/RW.8, Pulo Gadung, Kec. Pulo Gadung, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13260",
                "07:00 - 22:00",
                4.5f,
                R.drawable.placeholder,
                -5.235879125272809,
                106.79331736597358,
                new ArrayList<>(Arrays.asList(
                        R.drawable.placeholder,
                        R.drawable.placeholder,
                        R.drawable.placeholder
                )),
                "Jakarta Timur"
        ));

        cafes.add(new Cafe(
                "cafe18",
                "Bruen coffee & kitchen",
                "Jl. Monumen Pancasila Sakti No.9 6, RT.6/RW.8, Lubang Buaya, Kec. Cipayung, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13810",
                "08:00 - 22:00",
                4.5f,
                R.drawable.placeholder,
                -5.235879125272809,
                106.79331736597358,
                new ArrayList<>(Arrays.asList(
                        R.drawable.placeholder,
                        R.drawable.placeholder,
                        R.drawable.placeholder
                )),
                "Jakarta Timur"
        ));

        cafes.add(new Cafe(
                "cafe18",
                "Bruen coffee & kitchen",
                "Jl. Monumen Pancasila Sakti No.9 6, RT.6/RW.8, Lubang Buaya, Kec. Cipayung, Kota Jakarta Timur, Daerah Khusus Ibukota Jakarta 13810",
                "08:00 - 22:00",
                4.5f,
                R.drawable.placeholder,
                -5.235879125272809,
                106.79331736597358,
                new ArrayList<>(Arrays.asList(
                        R.drawable.placeholder,
                        R.drawable.placeholder,
                        R.drawable.placeholder
                )),
                "Jakarta Timur"
        ));

        return cafes;
    }
}
