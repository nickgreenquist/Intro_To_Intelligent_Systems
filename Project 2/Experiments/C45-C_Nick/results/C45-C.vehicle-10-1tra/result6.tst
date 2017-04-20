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
bus bus
van saab
bus bus
saab van
van van
opel opel
opel saab
saab opel
bus bus
saab saab
van van
opel saab
van van
bus bus
saab saab
opel opel
van van
saab bus
opel opel
saab saab
bus bus
saab bus
van van
bus bus
van van
opel opel
van van
saab van
saab saab
saab opel
opel opel
saab opel
bus bus
van van
van van
opel opel
bus bus
opel saab
saab opel
van van
opel opel
opel saab
saab opel
bus bus
opel opel
bus bus
van van
bus bus
opel saab
bus bus
opel saab
bus bus
bus bus
opel opel
saab saab
saab saab
van van
bus bus
saab saab
bus van
van van
opel saab
saab saab
van saab
van van
bus bus
saab saab
saab saab
van van
bus bus
saab saab
bus bus
saab opel
van bus
van van
bus bus
opel opel
bus bus
opel saab
opel opel
opel van
bus bus
opel saab
van van
