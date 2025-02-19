;Include polygon reduction function
Include "PolyReducer.bb"

;Set up the window and the graphics mode
AppTitle "Polygon Reduction Example"
Graphics3D 640,480,0,2
SetBuffer BackBuffer()

font=LoadFont("Arial",16)
bigfont=LoadFont("Verdana",26)

;Make a light pointing down from above
light=CreateLight()
RotateEntity light,60,45,0

;Create a camera
cam=CreateCamera()
PositionEntity cam,0,0,-5

;Load the example mesh
mesh=LoadMesh("chevelle.3ds")
PositionEntity mesh,0,0,0
RotateEntity mesh,10,220,0
ScaleEntity mesh,1,1,1

;Main loop
While True

	;Get the mouse's movement
	mys#=MouseYSpeed()
	mxs#=MouseXSpeed()
	
	;Left drag rotates
	If MouseDown(1) Then RotateEntity mesh,EntityPitch(mesh)+mys#,EntityYaw(mesh)+mxs#,0

	;Right drag moves
	If MouseDown(2) Then PositionEntity mesh,EntityX(mesh)+mxs#/100.0,EntityY(mesh)-mys#/100.0,0

	;Space to slowly reduce
	If KeyDown(57) Then
		time=MilliSecs()
		MeshPolygonsReduce(mesh,1) ;Reduce 1 polygon from mesh
		time=MilliSecs()-time
	End If
	
	;Delete to reduce a large amount of polys
	If KeyHit(211) Then
		Color 0,0,255
		Rect 190,140,230,30,False
		Color 50,50,50
		Rect 191,141,228,28,True
		Color 255,255,255
		
		Locate 198,147
		FlushKeys
		num=Input("How many polygons to reduce? ")
	
		Color 50,50,50
		Rect 191,141,228,28,True
		Color 255,255,255
		Locate 198,147
		Print "Reducing..."
		
		time=MilliSecs()
		MeshPolygonsReduce(mesh,num) ;Reduce num polygons from mesh
		time=MilliSecs()-time
	End If
	
	;ESC to restore mesh
	If KeyHit(1) Then
		xr#=EntityPitch(mesh)
		yr#=EntityYaw(mesh)
		zr#=EntityRoll(mesh)
		x#=EntityX(mesh)
		y#=EntityY(mesh)
		z#=EntityZ(mesh)
		FreeEntity mesh
		mesh=LoadMesh("chevelle.3ds")
		PositionEntity mesh,x,y,z
		RotateEntity mesh,xr,yr,zr
		ScaleEntity mesh,0.01,0.01,0.01
	End If
	
	;W for wireframe
	If KeyHit(17) Then
		WireFrame 1-w
		w=1-w
	End If
	

	RenderWorld
	
	;2D
	Color 64,128,255
	Rect 2,2,636,40,False
	Color 1,45,126
	Rect 3,3,634,38,True
	Color 80,200,80
	Line 0,400,640,400
	Color 255,255,255
	
	SetFont bigfont
	Text 320,9,"Polygon Reduction Example"

	SetFont font
	Text 6,5,"Triangle count: "+TrisRendered()
	Text 6,23,"Last process time: "+time+" millisecs"
	Text 10,410,"Hold Space to slowly reduce polygons in mesh"
	Text 10,430,"Press Delete to reduce any amount of polygons"
	Text 10,450,"Press W to toggle wireframe mode"
	Text 390,410,"Use left mouse button to rotate mesh"
	Text 390,430,"Use right mouse button to move mesh"
	Text 390,450,"Press Esc to restore mesh"
	
	Flip
Wend

End
;~IDEal Editor Parameters:
;~C#Blitz3D