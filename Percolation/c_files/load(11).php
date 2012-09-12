mw.loader.implement("jquery.effects.core",function(){;jQuery.effects||(function($,undefined){$.effects={};$.each(['backgroundColor','borderBottomColor','borderLeftColor','borderRightColor','borderTopColor','borderColor','color','outlineColor'],function(i,attr){$.fx.step[attr]=function(fx){if(!fx.colorInit){fx.start=getColor(fx.elem,attr);fx.end=getRGB(fx.end);fx.colorInit=true;}fx.elem.style[attr]='rgb('+Math.max(Math.min(parseInt((fx.pos*(fx.end[0]-fx.start[0]))+fx.start[0],10),255),0)+','+Math.max(Math.min(parseInt((fx.pos*(fx.end[1]-fx.start[1]))+fx.start[1],10),255),0)+','+Math.max(Math.min(parseInt((fx.pos*(fx.end[2]-fx.start[2]))+fx.start[2],10),255),0)+')';};});function getRGB(color){var result;if(color&&color.constructor==Array&&color.length==3)return color;if(result=/rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/.exec(color))return[parseInt(result[1],10),parseInt(result[2],10),parseInt(result[3],10)];if(result=
/rgb\(\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*\)/.exec(color))return[parseFloat(result[1])*2.55,parseFloat(result[2])*2.55,parseFloat(result[3])*2.55];if(result=/#([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})/.exec(color))return[parseInt(result[1],16),parseInt(result[2],16),parseInt(result[3],16)];if(result=/#([a-fA-F0-9])([a-fA-F0-9])([a-fA-F0-9])/.exec(color))return[parseInt(result[1]+result[1],16),parseInt(result[2]+result[2],16),parseInt(result[3]+result[3],16)];if(result=/rgba\(0, 0, 0, 0\)/.exec(color))return colors['transparent'];return colors[$.trim(color).toLowerCase()];}function getColor(elem,attr){var color;do{color=($.curCSS||$.css)(elem,attr);if(color!=''&&color!='transparent'||$.nodeName(elem,"body"))break;attr="backgroundColor";}while(elem=elem.parentNode);return getRGB(color);};var colors={aqua:[0,255,255],azure:[240,255,255],beige:[245,245,220],black:[0,0,0],blue:[0,0,255],brown:[165,42,42],cyan:[0,255,255],darkblue:[
0,0,139],darkcyan:[0,139,139],darkgrey:[169,169,169],darkgreen:[0,100,0],darkkhaki:[189,183,107],darkmagenta:[139,0,139],darkolivegreen:[85,107,47],darkorange:[255,140,0],darkorchid:[153,50,204],darkred:[139,0,0],darksalmon:[233,150,122],darkviolet:[148,0,211],fuchsia:[255,0,255],gold:[255,215,0],green:[0,128,0],indigo:[75,0,130],khaki:[240,230,140],lightblue:[173,216,230],lightcyan:[224,255,255],lightgreen:[144,238,144],lightgrey:[211,211,211],lightpink:[255,182,193],lightyellow:[255,255,224],lime:[0,255,0],magenta:[255,0,255],maroon:[128,0,0],navy:[0,0,128],olive:[128,128,0],orange:[255,165,0],pink:[255,192,203],purple:[128,0,128],violet:[128,0,128],red:[255,0,0],silver:[192,192,192],white:[255,255,255],yellow:[255,255,0],transparent:[255,255,255]};var classAnimationActions=['add','remove','toggle'],shorthandStyles={border:1,borderBottom:1,borderColor:1,borderLeft:1,borderRight:1,borderTop:1,borderWidth:1,margin:1,padding:1};function getElementStyles(){var style=document.defaultView?
document.defaultView.getComputedStyle(this,null):this.currentStyle,newStyle={},key,camelCase;if(style&&style.length&&style[0]&&style[style[0]]){var len=style.length;while(len--){key=style[len];if(typeof style[key]=='string'){camelCase=key.replace(/\-(\w)/g,function(all,letter){return letter.toUpperCase();});newStyle[camelCase]=style[key];}}}else{for(key in style){if(typeof style[key]==='string'){newStyle[key]=style[key];}}}return newStyle;}function filterStyles(styles){var name,value;for(name in styles){value=styles[name];if(value==null||$.isFunction(value)||name in shorthandStyles||(/scrollbar/).test(name)||(!(/color/i).test(name)&&isNaN(parseFloat(value)))){delete styles[name];}}return styles;}function styleDifference(oldStyle,newStyle){var diff={_:0},name;for(name in newStyle){if(oldStyle[name]!=newStyle[name]){diff[name]=newStyle[name];}}return diff;}$.effects.animateClass=function(value,duration,easing,callback){if($.isFunction(easing)){callback=easing;easing=null;}return this.
queue(function(){var that=$(this),originalStyleAttr=that.attr('style')||' ',originalStyle=filterStyles(getElementStyles.call(this)),newStyle,className=that.attr('class')||"";$.each(classAnimationActions,function(i,action){if(value[action]){that[action+'Class'](value[action]);}});newStyle=filterStyles(getElementStyles.call(this));that.attr('class',className);that.animate(styleDifference(originalStyle,newStyle),{queue:false,duration:duration,easing:easing,complete:function(){$.each(classAnimationActions,function(i,action){if(value[action]){that[action+'Class'](value[action]);}});if(typeof that.attr('style')=='object'){that.attr('style').cssText='';that.attr('style').cssText=originalStyleAttr;}else{that.attr('style',originalStyleAttr);}if(callback){callback.apply(this,arguments);}$.dequeue(this);}});});};$.fn.extend({_addClass:$.fn.addClass,addClass:function(classNames,speed,easing,callback){return speed?$.effects.animateClass.apply(this,[{add:classNames},speed,easing,callback]):this.
_addClass(classNames);},_removeClass:$.fn.removeClass,removeClass:function(classNames,speed,easing,callback){return speed?$.effects.animateClass.apply(this,[{remove:classNames},speed,easing,callback]):this._removeClass(classNames);},_toggleClass:$.fn.toggleClass,toggleClass:function(classNames,force,speed,easing,callback){if(typeof force=="boolean"||force===undefined){if(!speed){return this._toggleClass(classNames,force);}else{return $.effects.animateClass.apply(this,[(force?{add:classNames}:{remove:classNames}),speed,easing,callback]);}}else{return $.effects.animateClass.apply(this,[{toggle:classNames},force,speed,easing]);}},switchClass:function(remove,add,speed,easing,callback){return $.effects.animateClass.apply(this,[{add:add,remove:remove},speed,easing,callback]);}});$.extend($.effects,{version:"1.8.23",save:function(element,set){for(var i=0;i<set.length;i++){if(set[i]!==null)element.data("ec.storage."+set[i],element[0].style[set[i]]);}},restore:function(element,set){for(var i=0;
i<set.length;i++){if(set[i]!==null)element.css(set[i],element.data("ec.storage."+set[i]));}},setMode:function(el,mode){if(mode=='toggle')mode=el.is(':hidden')?'show':'hide';return mode;},getBaseline:function(origin,original){var y,x;switch(origin[0]){case'top':y=0;break;case'middle':y=0.5;break;case'bottom':y=1;break;default:y=origin[0]/original.height;};switch(origin[1]){case'left':x=0;break;case'center':x=0.5;break;case'right':x=1;break;default:x=origin[1]/original.width;};return{x:x,y:y};},createWrapper:function(element){if(element.parent().is('.ui-effects-wrapper')){return element.parent();}var props={width:element.outerWidth(true),height:element.outerHeight(true),'float':element.css('float')},wrapper=$('<div></div>').addClass('ui-effects-wrapper').css({fontSize:'100%',background:'transparent',border:'none',margin:0,padding:0}),active=document.activeElement;try{active.id;}catch(e){active=document.body;}element.wrap(wrapper);if(element[0]===active||$.contains(element[0],active)){$(
active).focus();}wrapper=element.parent();if(element.css('position')=='static'){wrapper.css({position:'relative'});element.css({position:'relative'});}else{$.extend(props,{position:element.css('position'),zIndex:element.css('z-index')});$.each(['top','left','bottom','right'],function(i,pos){props[pos]=element.css(pos);if(isNaN(parseInt(props[pos],10))){props[pos]='auto';}});element.css({position:'relative',top:0,left:0,right:'auto',bottom:'auto'});}return wrapper.css(props).show();},removeWrapper:function(element){var parent,active=document.activeElement;if(element.parent().is('.ui-effects-wrapper')){parent=element.parent().replaceWith(element);if(element[0]===active||$.contains(element[0],active)){$(active).focus();}return parent;}return element;},setTransition:function(element,list,factor,value){value=value||{};$.each(list,function(i,x){var unit=element.cssUnit(x);if(unit[0]>0)value[x]=unit[0]*factor+unit[1];});return value;}});function _normalizeArguments(effect,options,speed,
callback){if(typeof effect=='object'){callback=options;speed=null;options=effect;effect=options.effect;}if($.isFunction(options)){callback=options;speed=null;options={};}if(typeof options=='number'||$.fx.speeds[options]){callback=speed;speed=options;options={};}if($.isFunction(speed)){callback=speed;speed=null;}options=options||{};speed=speed||options.duration;speed=$.fx.off?0:typeof speed=='number'?speed:speed in $.fx.speeds?$.fx.speeds[speed]:$.fx.speeds._default;callback=callback||options.complete;return[effect,options,speed,callback];}function standardSpeed(speed){if(!speed||typeof speed==="number"||$.fx.speeds[speed]){return true;}if(typeof speed==="string"&&!$.effects[speed]){return true;}return false;}$.fn.extend({effect:function(effect,options,speed,callback){var args=_normalizeArguments.apply(this,arguments),args2={options:args[1],duration:args[2],callback:args[3]},mode=args2.options.mode,effectMethod=$.effects[effect];if($.fx.off||!effectMethod){if(mode){return this[mode](
args2.duration,args2.callback);}else{return this.each(function(){if(args2.callback){args2.callback.call(this);}});}}return effectMethod.call(this,args2);},_show:$.fn.show,show:function(speed){if(standardSpeed(speed)){return this._show.apply(this,arguments);}else{var args=_normalizeArguments.apply(this,arguments);args[1].mode='show';return this.effect.apply(this,args);}},_hide:$.fn.hide,hide:function(speed){if(standardSpeed(speed)){return this._hide.apply(this,arguments);}else{var args=_normalizeArguments.apply(this,arguments);args[1].mode='hide';return this.effect.apply(this,args);}},__toggle:$.fn.toggle,toggle:function(speed){if(standardSpeed(speed)||typeof speed==="boolean"||$.isFunction(speed)){return this.__toggle.apply(this,arguments);}else{var args=_normalizeArguments.apply(this,arguments);args[1].mode='toggle';return this.effect.apply(this,args);}},cssUnit:function(key){var style=this.css(key),val=[];$.each(['em','px','%','pt'],function(i,unit){if(style.indexOf(unit)>0)val=[
parseFloat(style),unit];});return val;}});var baseEasings={};$.each(["Quad","Cubic","Quart","Quint","Expo"],function(i,name){baseEasings[name]=function(p){return Math.pow(p,i+2);};});$.extend(baseEasings,{Sine:function(p){return 1-Math.cos(p*Math.PI/2);},Circ:function(p){return 1-Math.sqrt(1-p*p);},Elastic:function(p){return p===0||p===1?p:-Math.pow(2,8*(p-1))*Math.sin(((p-1)*80-7.5)*Math.PI/15);},Back:function(p){return p*p*(3*p-2);},Bounce:function(p){var pow2,bounce=4;while(p<((pow2=Math.pow(2,--bounce))-1)/11){}return 1/Math.pow(4,3-bounce)-7.5625*Math.pow((pow2*3-2)/22-p,2);}});$.each(baseEasings,function(name,easeIn){$.easing["easeIn"+name]=easeIn;$.easing["easeOut"+name]=function(p){return 1-easeIn(1-p);};$.easing["easeInOut"+name]=function(p){return p<.5?easeIn(p*2)/2:easeIn(p*-2+2)/-2+1;};});})(jQuery);;},{},{});mw.loader.implement("jquery.effects.highlight",function(){(function($,undefined){$.effects.highlight=function(o){return this.queue(function(){var elem=$(this),props=[
'backgroundImage','backgroundColor','opacity'],mode=$.effects.setMode(elem,o.options.mode||'show'),animation={backgroundColor:elem.css('backgroundColor')};if(mode=='hide'){animation.opacity=0;}$.effects.save(elem,props);elem.show().css({backgroundImage:'none',backgroundColor:o.options.color||'#ffff99'}).animate(animation,{queue:false,duration:o.duration,easing:o.options.easing,complete:function(){(mode=='hide'&&elem.hide());$.effects.restore(elem,props);(mode=='show'&&!$.support.opacity&&this.style.removeAttribute('filter'));(o.callback&&o.callback.apply(this,arguments));elem.dequeue();}});});};})(jQuery);;},{},{});

/* cache key: enwiki:resourceloader:filter:minify-js:7:3a93b795c16357e08de75041969b855e */
