local component = require "component"
local event = require "event"
local gpu = component.gpu
local call = component.invoke

colorGradient = {0x00FF00, 0x6BEE00, 0x95DB00, 0xB0C800, 0xC5B400, 0xD79F00, 0xE68700, 0xF46900, 0xFC4700, 0xFF0000}
coreHeatESTOP = true
coolantLossESTOP = true

runSig = true

coldCoolantLevel = 0
coldCoolantOutflow = 0
prevCoolantFlow = 0

hotCoolantLevel = 0
hotCoolantOutflow = 0
prevHotCoolantFlow = 0

gpu.fill(1,1,160,160," ")

-- Button Bullshit
function newButton(x, y, width, height, colorUp, colorDown, func)
    local button = {xpos = 0, ypos = 0, width = 0, height = 0, colorUp = 0, colorDown = 0, func = nil}
    button.xpos = x
    button.ypos = y
    button.width = width
    button.height = height
    button.colorUp = colorUp
    button.colorDown = colorDown
    button.func = func
    return button
end

function drawButton(button, color)
    component.gpu.setBackground(color)
    component.gpu.fill(button.xpos, button.ypos, button.width, button.height, " ")
    component.gpu.setBackground(0x000000)
end

pressedButton = nil
function buttonPress(_, _, x, y, _, _)
    for _, b in pairs(buttons) do
        if((x>=b.xpos) and (x<(b.xpos+b.width)) and (y>=b.ypos) and (y<(b.ypos+b.height)) ) then
            drawButton(b, b.colorDown)
            pressedButton = b
        end
    end
end

function buttonRelease(_, _, x, y, _, _)
    drawButton(pressedButton, pressedButton.colorUp)
    pressedButton.func()
    pressedButton = nil
end
--Button bullshit ends

buttons = {}

buttons[1] = newButton(61, 6, 6, 2, 0xFFFFFF, 0xAAAAAA, function() component.proxy(pwrController).setLevel(call(pwrController, "getLevel")+1) end)
buttons[2] = newButton(68, 6, 6, 2, 0xFFFFFF, 0xAAAAAA, function() component.proxy(pwrController).setLevel(call(pwrController, "getLevel")+5) end)
buttons[3] = newButton(75, 6, 6, 2, 0xFFFFFF, 0xAAAAAA, function() component.proxy(pwrController).setLevel(call(pwrController, "getLevel")+10) end)

buttons[4] = newButton(61, 9, 6, 2, 0xFFFFFF, 0xAAAAAA, function() component.proxy(pwrController).setLevel(call(pwrController, "getLevel")-1) end)
buttons[5] = newButton(68, 9, 6, 2, 0xFFFFFF, 0xAAAAAA, function() component.proxy(pwrController).setLevel(call(pwrController, "getLevel")-5) end)
buttons[6] = newButton(75, 9, 6, 2, 0xFFFFFF, 0xAAAAAA, function() component.proxy(pwrController).setLevel(call(pwrController, "getLevel")-10) end)

buttons[7] = newButton(82, 6, 11, 5, 0xFF0000, 0xAA0000, function() component.proxy(pwrController).setLevel(100) end)
buttons[8] = newButton(94, 6, 12, 2, 0x00FF00, 0x00AA00, function() coreHeatESTOP = not coreHeatESTOP if coreHeatESTOP == true then buttons[8].colorUp = 0x00FF00 buttons[8].colorDown = 0x00AA00 else buttons[8].colorUp = 0xFF0000 buttons[8].colorDown = 0xAA0000 end end)
buttons[9] = newButton(94, 9, 12, 2, 0x00FF00, 0x00AA00, function() coolantLossESTOP = not coolantLossESTOP if coolantLossESTOP == true then buttons[9].colorUp = 0x00FF00 buttons[9].colorDown = 0x00AA00 else buttons[9].colorUp = 0xFF0000 buttons[9].colorDown = 0xAA0000 end  end)

buttons[10] = newButton(107, 8, 5, 3, 0xFF0000, 0xAA0000, function() runSig = false end)

for address, _ in component.list("ntm_pwr_control") do
    pwrController = address
end

gpu.setForeground(0xAAAAAA)

--Control rods
gpu.fill(60,4,54,8,"█")

--Outlet
gpu.fill(91,13,16,8,"█")

--Inlet
gpu.fill(91,30,16,8,"█")

gpu.set(61,13,"    █████████████████████")
gpu.set(61,14,"     █ █ █ █ █ █ █ █ █ █")
gpu.set(61,15,"     █ █ █▄█▄█▄█▄█▄█ █ █")
gpu.set(61,16,"    ▄█████▀█▀█▀█▀█▀█████▄")
gpu.set(61,17,"  ▄███▀█ █ █ █ █ █ █ █▀███▄")
gpu.set(61,18," ▄██ █ █ █ █ █ █ █ █ █ █ ██▄")
gpu.set(61,19," ██                       ██")
gpu.set(61,20,"██▀ █████████████████████ ▀██")
gpu.set(61,21,"██  █████████████████████  ██▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄")
gpu.set(61,22,"██  █                   █  ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀")
gpu.set(61,23,"██  █████████████████████  → → → → → → → → → →")
gpu.set(61,24,"██  █                   █  ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄")
gpu.set(61,25,"██  █████████████████████  ██▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀")
gpu.set(61,26,"██  █                   █  ██")
gpu.set(61,27,"██  █████████████████████  ██")
gpu.set(61,28,"██  █                   █  ██")
gpu.set(61,29,"██  █████████████████████  ██")
gpu.set(61,30,"██  █                   █  ██")
gpu.set(61,31,"██  █████████████████████  ██")
gpu.set(61,32,"██                         ██")
gpu.set(61,33,"██                         ██")
gpu.set(61,34,"██                         ██")
gpu.set(61,35,"██                         ██")
gpu.set(61,36,"██                         ██")
gpu.set(61,37,"██                         ██")
gpu.set(61,38,"██                         ██▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄")
gpu.set(61,39,"██                         ▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀")
gpu.set(61,40,"██                         ← ← ← ← ← ← ← ← ← ←")
gpu.set(61,41,"██                         ▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄")
gpu.set(61,42,"██                         ██▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀")
gpu.set(61,43,"██▄                       ▄██")
gpu.set(61,44," ██                       ██")
gpu.set(61,45," ▀██                     ██▀")
gpu.set(61,46,"  ▀██▄▄               ▄▄██▀")
gpu.set(61,47,"    ▀▀███▄▄▄▄▄▄▄▄▄▄▄███▀▀")
gpu.set(61,48,"        ▀▀▀▀▀▀▀▀▀▀▀▀")

gpu.setBackground(0xAAAAAA)
gpu.setForeground(0x000000)

gpu.set(70,4,"CONTROL RODS")
gpu.set(61,5,"INS+1  INS+5  INS+10")
gpu.set(61,8,"RET+1  RET+5  RET+10")

gpu.set(85,5,"ESTOP")
gpu.set(107,5,"LEVEL")
gpu.set(107,7,"QUIT")

gpu.set(94,5,"OVHEAT ESTOP")
gpu.set(94,8,"NOCOOL ESTOP")

gpu.set(95,13,"OUTFLOW")
gpu.set(92,14,"BUFFER")
gpu.set(99,14,"HOTΔ")

gpu.set(95,30,"INFLOW")
gpu.set(92,31,"BUFFER")
gpu.set(99,31,"COOLΔ")

gpu.set(69,20,"REACTOR  CORE")
gpu.set(71,21,"CORE HEAT:")
gpu.set(71,23,"HULL HEAT:")
gpu.set(71,25,"CORE FLUX:")
gpu.set(68,27,"COLD HEATEX LVL:")
gpu.set(69,29,"HOT HEATEX LVL:")
gpu.setBackground(0x000000)

gpu.setForeground(0xFFFFFF)
gpu.fill(107,6,5,1,"█")

--Outflow Buffer
gpu.fill(92,15,6,5,"█")

--CoolDelta
gpu.fill(99,15,7,1,"█")

--HotDelta

gpu.set(66,19,"┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃")
gpu.fill(66,22,19,1,"█")
gpu.fill(66,24,19,1,"█")
gpu.fill(66,26,19,1,"█")
gpu.fill(66,28,19,1,"█")
gpu.fill(66,30,19,1,"█")
gpu.set(66,32,"┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃")
gpu.setForeground(0xAAAAAA)

gpu.setForeground(0x000000)
gpu.setBackground(0xFFFFFF)
gpu.set(83,22,"TU")
gpu.set(83,24,"TU")
gpu.setForeground(0xFFFFFF)
gpu.setBackground(0x000000)


event.listen("touch", buttonPress)
event.listen("drop", buttonRelease)

while (runSig == true) do
    rodLevel = call(pwrController, "getLevel")

    coreHeat, _ = call(pwrController, "getHeat")
    coreHeat = coreHeat//1000000

    for _, b in pairs(buttons) do
        drawButton(b, b.colorUp)
    end

    for j=rodLevel//10,10 do
        gpu.fill(64+(j*2), 33, 1, 10, " ")
    end

    for j=1,rodLevel//10 do
        gpu.fill(64+(j*2), 33, 1, 10, "┃")
    end

    gpu.fill(64+(math.ceil(rodLevel/10)*2), 33, 1, math.fmod(rodLevel,10), "┃")

    for j=0,20,2 do
        gpu.setForeground(colorGradient[coreHeat+1])
        gpu.fill(65+j, 33, 1, 9, "█")
        gpu.setForeground(0xAAAAAA)
    end

    gpu.setBackground(0xFFFFFF)

    gpu.setForeground(0xFFFFFF)
    gpu.fill(66,22,19,1,"█")
    gpu.fill(66,24,19,1,"█")
    gpu.fill(66,26,19,1,"█")
    gpu.fill(66,28,19,1,"█")
    gpu.fill(66,30,19,1,"█")

    gpu.fill(92,15,6,5,"█")
    gpu.fill(92,32,6,5,"█")

    gpu.fill(99,15,7,1,"█")
    gpu.fill(99,32,7,1,"█")

    prevCoolantFlow = coldCoolantLevel
    prevHotCoolantFlow = hotCoolantLevel

    fullCoreHeat, fullHullHeat = call(pwrController, "getHeat")
    coldCoolantLevel, _, hotCoolantLevel, _ = call(pwrController, "getCoolantInfo")
    
    coldCoolantOutflow = coldCoolantLevel - prevCoolantFlow
    hotCoolantOutflow = hotCoolantLevel - prevHotCoolantFlow

    gpu.setForeground(0xFF0099)
    gpu.fill(92,15+(5-hotCoolantLevel//25600),6,hotCoolantLevel//25600, "█")
    gpu.setForeground(0x000000)

    gpu.setForeground(0x00FFFF)
    gpu.fill(92,32+(5-coldCoolantLevel//25600),6,coldCoolantLevel//25600, "█")
    gpu.setForeground(0x000000)

    gpu.set(66,22,tostring(fullCoreHeat))
    gpu.set(66,24,tostring(fullHullHeat))
    gpu.set(66,26,tostring(call(pwrController, "getFlux")))
    gpu.set(66,28,tostring(coldCoolantLevel))
    gpu.set(66,30,tostring(hotCoolantLevel))

    gpu.set(99,15,tostring(hotCoolantOutflow))
    gpu.set(99,32,tostring(coldCoolantOutflow))

    gpu.set(107,6,"   ")
    gpu.set(107,6,tostring(call(pwrController, "getLevel")))

    gpu.setBackground(0x000000)
    gpu.setForeground(0xFFFFFF)

    if (coreHeatESTOP == true) and (fullCoreHeat) > 9000000 then
        component.proxy(pwrController).setLevel(100)
    end

    if (coolantLossESTOP == true) and (coldCoolantLevel) < 10000 then
        component.proxy(pwrController).setLevel(100)
    end

    os.sleep(0.25)
end

event.ignore("touch", buttonPress)
event.ignore("drop", buttonRelease)

gpu.fill(1,1,160,160," ")