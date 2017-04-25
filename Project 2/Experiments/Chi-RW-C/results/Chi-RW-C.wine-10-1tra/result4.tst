@relation  wine
@attribute Alcohol real[11.0,14.9]
@attribute MalicAcid real[0.7,5.8]
@attribute Ash real[1.3,3.3]
@attribute AlcalinityOfAsh real[10.6,30.0]
@attribute Magnesium real[70.0,162.0]
@attribute TotalPhenols real[0.9,3.9]
@attribute flavanoids real[0.3,5.1]
@attribute NonflavanoidsPhenols real[0.1,0.7]
@attribute Proanthocyanins real[0.4,3.6]
@attribute ColorIntensity real[1.2,13.0]
@attribute Hue real[0.4,1.8]
@attribute OD280/OD315 real[1.2,4.0]
@attribute Proline real[278.0,1680.0]
@attribute Class{1,2,3}
@inputs Alcohol,MalicAcid,Ash,AlcalinityOfAsh,Magnesium,TotalPhenols,flavanoids,NonflavanoidsPhenols,Proanthocyanins,ColorIntensity,Hue,OD280/OD315,Proline
@outputs Class
@data
1 1
1 1
1 1
1 1
1 1
1 1
2 2
2 2
2 ?
2 2
2 2
2 2
2 2
3 3
3 3
3 3
3 3