@relation  vehicle
@attribute Compactness integer[73,119]
@attribute Circularity integer[33,59]
@attribute Distance_circularity integer[40,112]
@attribute Radius_ratio integer[104,333]
@attribute Praxis_aspect_ratio integer[47,138]
@attribute Max_length_aspect_ratio integer[2,55]
@attribute Scatter_ratio integer[112,265]
@attribute Elongatedness integer[26,61]
@attribute Praxis_rectangular integer[17,29]
@attribute Length_rectangular integer[118,188]
@attribute Major_variance integer[130,320]
@attribute Minor_variance integer[184,1018]
@attribute Gyration_radius integer[109,268]
@attribute Major_skewness integer[59,135]
@attribute Minor_skewness integer[0,22]
@attribute Minor_kurtosis integer[0,41]
@attribute Major_kurtosis integer[176,206]
@attribute Hollows_ratio integer[181,211]
@attribute Class{van,saab,bus,opel}
@inputs Compactness,Circularity,Distance_circularity,Radius_ratio,Praxis_aspect_ratio,Max_length_aspect_ratio,Scatter_ratio,Elongatedness,Praxis_rectangular,Length_rectangular,Major_variance,Minor_variance,Gyration_radius,Major_skewness,Minor_skewness,Minor_kurtosis,Major_kurtosis,Hollows_ratio
@outputs Class
@data
van van
saab saab
opel opel
van opel
bus bus
bus bus
van van
bus bus
opel opel
bus van
van van
opel saab
van van
bus bus
van van
opel saab
opel opel
opel saab
van van
bus bus
saab bus
bus bus
bus bus
bus bus
saab saab
saab bus
van van
bus bus
bus bus
saab saab
opel saab
opel opel
van van
bus bus
opel van
van van
bus bus
bus bus
saab saab
opel saab
van van
van saab
bus van
saab saab
van van
opel bus
opel saab
saab bus
opel bus
saab opel
van van
opel opel
van saab
opel opel
saab saab
bus bus
opel van
bus bus
saab bus
bus bus
saab bus
opel opel
opel opel
saab van
bus bus
van saab
van van
van van
bus bus
saab bus
van van
bus bus
saab saab
saab opel
opel opel
opel bus
bus bus
opel van
saab van
saab opel
saab bus
saab saab
saab opel
van van