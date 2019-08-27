function merge(target, source){
	for(let key in source){
		if(key in source && key in target){
			merge(target[key],source[key])
		}
		else{
			target[key] = source[key]
		}
	}
}

o1 = {}
o2 = {a:1, "__proto__": {b:2}}

merge(o1,o2)
console.log(o1.a,o1.b)

o3 = {}
console.log(o3.b)
