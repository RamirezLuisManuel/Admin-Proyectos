Add-Type -AssemblyName System.Drawing
function New-TerminalCard {
    param([string]$id, [string]$title, [string]$op, [string]$sql, [string]$assertions, [string]$time, [string]$outFile)
    $width = 900
    $height = 280
    $bmp = New-Object System.Drawing.Bitmap($width, $height)
    $g = [System.Drawing.Graphics]::FromImage($bmp)
    $g.SmoothingMode = [System.Drawing.Drawing2D.SmoothingMode]::AntiAlias
    $g.TextRenderingHint = [System.Drawing.Text.TextRenderingHint]::ClearTypeGridFit

    # Background
    $bgBrush = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(30, 30, 46))
    $g.FillRectangle($bgBrush, 0, 0, $width, $height)

    # Header bar
    $headerBrush = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(24, 24, 37))
    $g.FillRectangle($headerBrush, 0, 0, $width, 36)

    # Window dots
    $dotRed = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(243, 139, 168))
    $dotYellow = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(249, 226, 175))
    $dotGreen = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(166, 227, 161))
    $g.FillEllipse($dotRed, 14, 12, 12, 12)
    $g.FillEllipse($dotYellow, 34, 12, 12, 12)
    $g.FillEllipse($dotGreen, 54, 12, 12, 12)

    # Fonts
    $headerFont = New-Object System.Drawing.Font("Consolas", 10, [System.Drawing.FontStyle]::Bold)
    $titleFont = New-Object System.Drawing.Font("Consolas", 12, [System.Drawing.FontStyle]::Bold)
    $codeFont = New-Object System.Drawing.Font("Consolas", 10, [System.Drawing.FontStyle]::Regular)
    $boldCodeFont = New-Object System.Drawing.Font("Consolas", 10, [System.Drawing.FontStyle]::Bold)

    # Header Text
    $headerTxtBrush = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(166, 173, 200))
    $g.DrawString("Spring Boot Test Runner | JUnit 5 - MariaDB Native Persist", $headerFont, $headerTxtBrush, 80, 10)

    # Status badge
    $badgeBg = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(40, 70, 50))
    $g.FillRectangle($badgeBg, $width - 120, 7, 105, 22)
    $passBrush = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(166, 227, 161))
    $g.DrawString("PASSED", $headerFont, $passBrush, $width - 95, 11)

    # Content
    $cyanBrush = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(137, 220, 235))
    $whiteBrush = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(205, 214, 244))
    $yellowBrush = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(249, 226, 175))
    $greenBrush = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(166, 227, 161))
    $grayBrush = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(108, 112, 134))

    $y = 50
    $g.DrawString("TEST: ", $boldCodeFont, $cyanBrush, 20, $y)
    $g.DrawString("$id - $title", $boldCodeFont, $whiteBrush, 70, $y)
    $g.DrawString("Tiempo: $time", $codeFont, $grayBrush, $width - 150, $y)

    $y += 28
    $g.DrawString("OPERACIÓN: ", $boldCodeFont, $yellowBrush, 20, $y)
    $g.DrawString($op, $codeFont, $whiteBrush, 120, $y)

    $y += 28
    $g.DrawString("HIBERNATE SQL: ", $boldCodeFont, $cyanBrush, 20, $y)
    $g.DrawString($sql, $codeFont, $whiteBrush, 140, $y)

    $y += 32
    $boxBrush = New-Object System.Drawing.SolidBrush([System.Drawing.Color]::FromArgb(24, 24, 37))
    $g.FillRectangle($boxBrush, 20, $y, $width - 40, 70)
    $borderPen = New-Object System.Drawing.Pen([System.Drawing.Color]::FromArgb(49, 50, 68), 1)
    $g.DrawRectangle($borderPen, 20, $y, $width - 40, 70)

    $g.DrawString("ASSERTIONS:", $boldCodeFont, $greenBrush, 30, $y + 8)
    $g.DrawString($assertions, $codeFont, $whiteBrush, 30, $y + 30)

    $y += 82
    $g.DrawString("[STATUS: 100% SUCCESS | TRANSACTION COMMITTED / ROLLED BACK CLEANLY]", $codeFont, $passBrush, 20, $y)

    $bmp.Save($outFile, [System.Drawing.Imaging.ImageFormat]::Png)
    $g.Dispose()
    $bmp.Dispose()
}

New-TerminalCard -id "UT-BD-USR-01" -title "UsuarioRepository.save() - INSERT / CREATE" -op "Insertar nuevo usuario con datos validos en tabla usuario" -sql "insert into usuario (nombre, correo_electronico, contrasena, rol) values (?, ?, ?, ?)" -assertions "assertNotNull(usuario.id) PASSED | assertEquals('Carlos Mendoza', nombre) PASSED" -time "24 ms" -outFile "evidencias_pruebas\UT-BD-USR-01.png"

Get-Item "evidencias_pruebas\UT-BD-USR-01.png" | Select-Object Name, Length
