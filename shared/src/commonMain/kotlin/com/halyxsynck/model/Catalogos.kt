package com.halyxsynck.model

object Catalogos {

    val medicamentosComunes = listOf(
        "Paracetamol", "Ibuprofeno", "Metformina", "Losartán", "Enalapril",
        "Amoxicilina", "Omeprazol", "Loratadina", "Salbutamol", "Metoprolol",
        "Atorvastatina", "Aspirina", "Diclofenaco", "Captopril", "Furosemida",
        "Insulina", "Levotiroxina", "Clonazepam", "Sertralina", "Ranitidina",
        "Ciprofloxacino", "Azitromicina", "Prednisona", "Hidroclorotiazida",
        "Naproxeno", "Ketorolaco", "Dexametasona", "Simvastatina", "Glibenclamida",
        "Warfarina", "Cetirizina", "Fexofenadina", "Pantoprazol", "Esomeprazol",
        "Amlodipino", "Bisoprolol", "Clopidogrel", "Rosuvastatina", "Gabapentina",
        "Pregabalina", "Alprazolam", "Fluoxetina", "Paroxetina", "Escitalopram",
        "Trazodona", "Melatonina", "Ácido fólico", "Vitamina D", "Vitamina B12",
        "Complejo B", "Loperamida", "Butilhioscina", "Metamizol", "Diclofenaco gel",
        "Salmeterol", "Budesonida", "Montelukast", "Cefalexina", "Trimetoprima con sulfametoxazol"
    )

    val dosisComunes = listOf(
        "1 mg", "2.5 mg", "5 mg", "10 mg", "15 mg", "20 mg", "25 mg",
        "30 mg", "40 mg", "50 mg", "75 mg", "100 mg", "150 mg",
        "200 mg", "250 mg", "300 mg", "400 mg", "500 mg", "600 mg",
        "750 mg", "850 mg", "875 mg", "1 g", "1.5 g", "2 g",
        "2.5 ml", "5 ml", "7.5 ml", "10 ml", "15 ml", "20 ml", "30 ml",
        "1 tableta", "2 tabletas", "1 cápsula", "2 cápsulas",
        "1 gota", "2 gotas", "1 aplicación", "1 puff", "2 puffs"
    )

    val padecimientosComunes = listOf(
        // Respiratorio
        "Gripe", "Resfriado común", "Asma", "Bronquitis", "Neumonía", "Sinusitis",
        "Rinitis alérgica", "EPOC", "Congestión nasal", "Fibrosis pulmonar",
        "Tuberculosis", "Apnea del sueño", "Bronquiolitis", "Enfisema pulmonar",

        // Otorrinolaringología
        "Faringitis", "Amigdalitis", "Dolor de oído", "Infección de garganta",
        "Dolor de garganta", "Otitis", "Vértigo posicional", "Pérdida de audición",
        "Zumbido en el oído", "Desviación de tabique",

        // Cardiovascular
        "Hipertensión arterial", "Taquicardia", "Arritmia", "Insuficiencia cardíaca",
        "Colesterol alto", "Triglicéridos altos", "Varices", "Angina de pecho",
        "Soplo cardíaco", "Trombosis venosa", "Aneurisma",

        // Endocrino / metabólico
        "Diabetes", "Hipotiroidismo", "Hipertiroidismo", "Obesidad",
        "Resistencia a la insulina", "Bocio", "Síndrome metabólico",
        "Insuficiencia suprarrenal", "Hipoglucemia",

        // Gastrointestinal
        "Gastritis", "Colitis", "Reflujo gastroesofágico", "Estreñimiento",
        "Diarrea crónica", "Síndrome de intestino irritable", "Úlcera gástrica",
        "Hemorroides", "Hígado graso", "Náuseas", "Pancreatitis", "Cálculos biliares",
        "Hepatitis", "Enfermedad de Crohn", "Intolerancia a la lactosa", "Celiaquía",

        // Reumatología
        "Artritis", "Artrosis", "Fibromialgia", "Lupus", "Gota",
        "Espondilitis anquilosante", "Síndrome de Sjögren",

        // Ortopedia y Traumatología
        "Osteoporosis", "Dolor de espalda crónico", "Ciática", "Tendinitis",
        "Fractura", "Esguince", "Escoliosis", "Hernia discal", "Bursitis",
        "Túnel carpiano",

        // Dermatológico
        "Acné", "Psoriasis", "Vitíligo", "Dermatitis", "Eczema", "Urticaria",
        "Hongos en la piel", "Rosácea", "Caída del cabello", "Verrugas", "Melasma",

        // Neurológico
        "Migraña", "Epilepsia", "Vértigo", "Dolor de cabeza frecuente",
        "Parálisis facial", "Temblor", "Neuropatía", "Alzheimer", "Parkinson",
        "Esclerosis múltiple",

        // Mental / psicológico
        "Ansiedad", "Depresión", "Insomnio", "Estrés crónico", "Trastorno bipolar",
        "Trastorno de pánico", "Trastorno obsesivo compulsivo", "Fobias",

        // Ginecológico
        "Embarazo", "Menstruación irregular", "Síndrome premenstrual", "Menopausia",
        "Quistes ováricos", "Endometriosis", "Miomas uterinos", "Infertilidad",

        // Oftalmológico
        "Conjuntivitis", "Miopía", "Vista cansada", "Cataratas", "Glaucoma",
        "Ojo seco", "Astigmatismo",

        // Renal
        "Enfermedad renal crónica", "Cálculos renales", "Insuficiencia renal",

        // Urológico
        "Infección urinaria", "Prostatitis", "Disfunción eréctil",
        "Incontinencia urinaria",

        // Sangre / oncológico
        "Anemia", "Cáncer", "Leucemia", "Trombocitopenia", "Hemofilia",

        // Infeccioso
        "Infección viral", "Infección bacteriana", "COVID-19", "Dengue",
        "Mononucleosis", "Herpes",

        // Alergias
        "Alergias", "Alergia alimentaria", "Alergia a medicamentos",
        "Dermatitis atópica",

        // Dental
        "Caries", "Gingivitis", "Dolor de muela", "Sensibilidad dental",

        // Otros / generales
        "Fiebre", "Fatiga crónica", "Mareo", "Deshidratación", "Desnutrición",
        "Sobrepeso"
    )
}